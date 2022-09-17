import React, {useEffect, useState} from 'react';
import {Button, Nav} from "react-bootstrap";
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome'
import {faCircleUser} from "@fortawesome/free-solid-svg-icons";
import styles from './Profile.module.css'
import {useNavigate, useParams} from "react-router-dom";
import userService from "../../services/api/user-service";
import VideoCard from "../Videos/VideoCard";
import PlaylistCard from "../Playlists/PlaylistCard";
import authService from "../../services/api/auth-service";
import subscriptionService from "../../services/api/subscription-service";
import Swal from "sweetalert2";

const Profile = (props) => {

    const navigate = useNavigate();
    const {id} = useParams();
    const [channelInfo, setChannelInfo] = useState({channelName: "", subscriptionNumber: "", id: "", username: ""});
    const [videoList, setVideoList] = useState([]);
    const [playlists, setPlaylists] = useState([]);
    const [subscribed, setSubscribed] = useState(false);

    useEffect(()=>{

        userService.getChannelById(id)
            .then(data => {
                setSubscribed(data.subscribed)
                if(props.playlistcheck){
                    setChannelInfo(data.channel);
                    setPlaylists(data.channel.playlist);
                }else if(!props.playlistcheck){
                    setChannelInfo(data.channel);
                    setVideoList(data.videos);
                }
            })
    }, [props.playlistcheck, id]);

    let videos = videoList.map((video , index) =>{
        return <VideoCard videoPath={video.videoPath}
                          id={video.id}
                          name={video.name}
                          date={video.date}
                          views={video.views}
                          key={index}
                          marginRight="me-3"
                          ageRestricted={video.ageRestricted}
                          userId={video.user.id}/>
    })

    let playlistRender = playlists.map(( playlist , index) =>{
        return <PlaylistCard
            key={index}
            privatePlaylist={playlist.privatePlaylist}
            id={playlist.id}
            videoCount={playlist.videos.length}
            name={playlist.name}/>;
    })

    if(videos.length === 0){
        videos = <h5>There are no videos on this channel.</h5>
    }

    if(playlists.length === 0){
        playlistRender = <h5>There are no playlists on this channel.</h5>
    }

    const subscribe = () => {
        if(authService.isLoggedIn() === false){
            return Swal.fire({
                icon: 'error',
                title: 'Not logged in',
                text: 'You need to log in first.',
                showCancelButton: true,
                confirmButtonText: 'Continue',
            }).then((result) => {
                if (result.isConfirmed){
                    navigate("/login");
                }
            })
        }
        subscriptionService.subscribe(id).then(data=>{
            setSubscribed(data);
        })
    }


    return (
        <div className="d-flex flex-column mt-2 flex-wrap" style={{marginBottom: "265px"}}>
            <div className="d-flex flex-row flex-wrap" style={{height: '230px'}}>
                <div className="d-flex flex-row align-items-center"  style={{background:'white',  width: '100%'}}>
                    <div style={{width: "20%"}}>
                        <FontAwesomeIcon style={{width: "100%", height: "50%", color: 'gray'}} icon={faCircleUser} />
                    </div>
                    <div style={{width: '80%', marginLeft: '1rem', background: 'white'}}>
                        <div >
                            <p className={styles.channelName}>{channelInfo.channelName}</p>
                            <p className={styles.subscribeInfo}>{channelInfo.subscriptionNumber} subscribers</p>
                        </div>
                        {authService.getUsernameFromJwt() === channelInfo.username && subscribed === false ? null
                            :authService.getUsernameFromJwt() !== channelInfo.username && subscribed ?
                                <div>
                                    <Button variant="primary" onClick={subscribe}>Subscribed</Button>
                                </div>
                                :authService.getUsernameFromJwt() !== channelInfo.username && !subscribed ?
                                <div>
                                    <Button variant="danger" onClick={subscribe}>Subscribe</Button>
                                </div>
                                    : null }
                    </div>

                </div>
            </div>
            <div style={{marginTop: '3rem'}} >
                <Nav variant="tabs"  defaultActiveKey={props.playlistcheck ? `/playlists/channel/${id}` : `/videos/channel/${id}`}>
                    <Nav.Item>
                        <Nav.Link href={`/videos/channel/${id}`}>Videos</Nav.Link>
                    </Nav.Item>
                    <Nav.Item>
                        <Nav.Link  href={`/playlists/channel/${id}`}>Playlists</Nav.Link>
                    </Nav.Item>
                </Nav>
            </div>
            <div className=" w-70 mt-4 d-flex flex-wrap flex-row ">
                {props.playlistcheck ? playlistRender : videos}
            </div>
        </div>
    );
};

export default Profile;