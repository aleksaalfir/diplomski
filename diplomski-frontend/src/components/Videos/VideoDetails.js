import React, {useEffect, useState} from 'react';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome'
import {faEye, faFlag, faPlus, faShare, faThumbsUp, faUser} from "@fortawesome/free-solid-svg-icons";
import {Button} from "react-bootstrap";
import {Link, useNavigate, useParams} from "react-router-dom";
import videoService from "../../services/api/video-service";
import moment from "moment";
import styles from './VideoDetails.module.css'
import Swal from "sweetalert2";
import Modal from 'react-modal';
import VideoDetailsVideos from "./VideoDetailsVideos";
import VideoDetailsComments from "./VideoDetailsComments";
import PlaylistService from "../../services/api/playlist-service";
import ReportModal from "./ReportModal/ReportModal";
import authService from "../../services/api/auth-service";
import PrivateVideoPasswordModal from "./PrivateVideoPasswordModal/PrivateVideoPasswordModal";

const customStyles = {
    overlay: {
        backgroundColor: 'rgba(119,119,119,0.7)'
    },
    content: {
        top: '50%',
        left: '50%',
        right: 'auto',
        bottom: 'auto',
        marginRight: '-50%',
        transform: 'translate(-50%, -50%)',

        boxShadow: '0 0 10px rgba(0,0,0,0.6)',
        MozBoxShadow: "0 0 10px rgba(0,0,0,0.6)",
        WebkitBoxShadow: "0 0 10px rgba(0,0,0,0.6)",
        OBoxShadow: "0 0 10px rgba(0,0,0,0.6)",
    },

};


const VideoDetails = () => {

    const {id} = useParams();
    const [video, setVideo] = useState({id: "",
                                                name: "",
                                                user: {channelName: "", subscriptionNumber: "", id: ""},
                                                privateVideo: false,
                                                ageRestricted: false,
                                                description: "",
                                                videoPath: "",
                                                views: 0,
                                                date: new Date().toISOString(),
                                                videoComments: [],
                                                privatePassword: ""});
    const [videos, setVideos] = useState([]);
    const [toggleDescription, setToggleDescription] = useState(false);
    const [videoComment, setVideoComment] = useState({videoId: id, comment:""});
    const navigate = useNavigate();
    const [modalIsOpen, setModalIsOpen] = useState(false);
    const [myPlaylists, setMyPlaylists] = useState([]);
    const [playlistModal, setPlaylistModal] = useState(false);
    const [reportModal, setReportModal] = useState(false);
    const [liked, setLiked] = useState(false);
    const [privateVideoModalOpen, setPrivateVideoModalOpen] = useState(false);

    useEffect(() =>{
        videoService.getOne(id)
            .then(data =>{
            if (data.video.privateVideo === true) setPrivateVideoModalOpen(true);
            if(data.liked){
                setLiked(data.liked)
            }
            setVideo(data.video);
            setVideos(data.videos);
            setMyPlaylists(data.myPlaylists);
            return () =>{}
        })
    }, [id]);

    const like = () => {
        if(authService.isLoggedIn() === false){
            return Swal.fire({
                icon: 'error',
                title: 'Not logged in',
                text: 'You need to log in first.',
                showCancelButton: true,
                confirmButtonText: 'Continue',
            }).then((result) => {
                if (result.isConfirmed){
                    return navigate("/login");
                }
            })
        }
      videoService.like(id)
          .then(data=> {
              setLiked(data);
          });
    }

    function reportModalHandler(){
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
        reportModal ? setReportModal(false) : setReportModal(true)
    }

    function playlistModalHandler(){
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
        playlistModal ? setPlaylistModal(false) : setPlaylistModal(true)
    }

    function modalStateHandler(){
        modalIsOpen ? setModalIsOpen(false) : setModalIsOpen(true)
    }

    const toggleDescriptionHandler = () =>{
        toggleDescription ? setToggleDescription(false) : setToggleDescription(true);
    }

    const copyCurrentUrlHandler = () =>{
        navigator.clipboard.writeText(window.location.href);
        Swal.fire({
            position: 'top',
            title: 'Link is copied to clipboard.',
            timer: 1000,
            showConfirmButton: false,
            backdrop: false
        })
    }

    const changeCommentHandler = (value) =>{
        setVideoComment( videoComment => ({
            ...videoComment, comment: value
        }))
    }

    const submitCommentHandler = () =>{
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
        videoService.comment(videoComment).then(response => window.location.reload()).catch(err => console.log(err));
    }

    const deleteVideoHandler = () =>{
        Swal.fire({
            icon: 'error',
            title: 'Do you really want to delete video?',
            showCancelButton: true,
            confirmButtonText: 'Delete',
        }).then((result) => {
            if (result.isConfirmed) {
                videoService.deleteVideo(id).then(data =>{
                    Swal.fire({
                        position: 'center',
                        icon: 'success',
                        title: 'Video is deleted!',
                        showConfirmButton: false,
                        timer: 3000
                    })
                    navigate("/home");
                }).catch(err => console.log(err));
            }
        })
    }

    const updateVideoHandler = () => {
        videoService.update(id, video)
            .then(data=>{
                Swal.fire({
                    position: 'center',
                    icon: 'success',
                    title: 'Video updated!',
                    showConfirmButton: false,
                    timer: 2000
                }).then(()=>window.location.reload());

            })
    }

    const privateHandler = () =>{
        const opositeBoolean = !video.privateVideo;
        setVideo(video =>({...video, privateVideo: opositeBoolean }));
    }

    const ageRestrictedHandler = () =>{
        const opositeBoolean = !video.ageRestricted;
        setVideo(video =>({...video, ageRestricted: opositeBoolean }));
    }

    const addVideoToPlaylistHandler = (playlistId, videoId) => {
        if(video.privateVideo){
            alert("You can't add private video to playlist.")
            return;
        }
        PlaylistService.addVideoToPlaylist(playlistId, videoId)
            .then(res=>{
                if(res.status === 200) {
                    window.location.reload();
                }else{
                    alert("You already have this video in selected playlist.")
                }
            })
    }

    return (
        <div className="d-flex flex-wrap">
            <div  style={{width:'75%',background:'white', padding: '10px'}}>
                <div style={{width: '100%', marginTop: '1rem', padding:'0px', height: '70vh', backgroundColor: 'black'}}>
                    <video autoPlay={true} style={{width: '100%' ,height: '100%'}} controls src={video.videoPath}>
                    </video>
                </div>
                <div className="d-flex" style={{padding:'10px'}}>
                    <div style={{width:'70%'}}>
                        <h4>{video.name}</h4>
                        <span>{video.views} <FontAwesomeIcon icon={faEye} />
                            <span style={{margin: "0 5px"}}>&#183;</span>
                            {moment(video.date).format("L")} </span>
                    </div>
                    <div style={{width:'30%', fontSize: "2rem"}} className="justify-content-end align-items-end d-flex">
                        <FontAwesomeIcon className={liked ? [styles.iconLiked, "me-2"].join(" ") : [styles.icon, "me-2"].join(" ")} onClick={like} icon={faThumbsUp} />
                        <FontAwesomeIcon onClick={playlistModalHandler} className={[styles.icon, "me-2"].join(" ")} icon={faPlus} />
                        <FontAwesomeIcon onClick={reportModalHandler} className={[styles.icon, "me-2"].join(" ")} icon={faFlag} />
                        <FontAwesomeIcon onClick={copyCurrentUrlHandler} className={styles.icon} icon={faShare} />
                    </div>
                </div>
                <hr/>
                <div>
                    <div className="d-flex align-items-center" style={{height: '5rem', width: '100%'}}>
                        <div className="d-flex align-items-center justify-content-center" style={{borderRadius:'50%', background: "gray", width: '10%', height: '100%'}}>
                            <FontAwesomeIcon style={{fontSize: '3.4rem'}} icon={faUser} />
                        </div>
                        <div className="d-flex ms-2 flex-column " style={{width: '90%', fontSize:'1.3rem'}}>
                            <Link style={{textDecoration: "none", color: "black"}} to={`/channel/${video.user.id}`}>{video.user.channelName}</Link>
                            <div className="fs-6">{video.user.subscriptionNumber} subscribers</div>
                        </div>
                    </div>
                    <div className={toggleDescription ? "mt-3" : "d-none"}>
                        {video.description}
                        {authService.isUser() ?
                            <div className="mt-2">
                                <Button className="me-1" variant="secondary" onClick={modalStateHandler}>Edits</Button>
                                <Button variant="danger" onClick={deleteVideoHandler}>Delete</Button>
                            </div> : null
                        }
                    </div>
                    <div className="text-center mt-3"><span className={styles.showMore} onClick={toggleDescriptionHandler}>{toggleDescription ? "Show less..." : "Show more..."}</span></div>
                    <hr/>
                </div>
                <VideoDetailsComments videoComments={video.videoComments} changeCommentHandler={changeCommentHandler} submitCommentHandler={submitCommentHandler}/>
            </div>
            <VideoDetailsVideos videos={videos} videoId={video.id}/>
            <Modal
                isOpen={modalIsOpen}
                onRequestClose={modalStateHandler}
                style={customStyles}
            >
                <h2 className="text-center">Update video</h2>
                <div>
                    <ul style={{listStyleType: "none"}}>
                        <li>
                            <label className="w-100" style={{fontWeight: "bold"}}>Name</label>
                            <input className="w-75" type="text" name="name" value={video.name} onChange={(event) => setVideo(video =>({...video, name: event.target.value}))}/>
                        </li>
                        <li>
                            <label className="w-100" style={{fontWeight: "bold"}}>Description</label>
                            <textarea value={video.description} rows="5" cols="50"  onChange={(event) => setVideo(video =>({...video, description: event.target.value}))}/>
                        </li>
                        <li>
                            <label  style={{fontWeight: "bold", marginRight: "10px"}}>Age restricted</label>
                            <input  type="checkbox" name="ageRestricted" checked={video.ageRestricted} onChange={ageRestrictedHandler}/>
                        </li>
                        <li>
                            <label  style={{fontWeight: "bold", marginRight: "10px"}}>Private</label>
                            <input  type="checkbox" name="privateVideo" checked={video.privateVideo} onChange={privateHandler}/>
                        </li>
                    </ul>
                    <div className="text-center">
                        <Button style={{marginRight: "5px"}} onClick={updateVideoHandler}>Update</Button>
                        <Button variant="secondary" onClick={modalStateHandler}>Cancel</Button>
                    </div>
                </div>
            </Modal>
            <Modal
                isOpen={playlistModal}
                onRequestClose={playlistModalHandler}
                style={customStyles}
                ariaHideApp={false}
            >
            <div className="d-flex flex-column" style={{width: '20rem'}}>
                <h6 className="text-center fs-5 mb-3">Add to playlist</h6>
                {myPlaylists ? myPlaylists.map((playlist, index)=>{
                    return (
                        <div key={playlist.id} className="d-flex mb-3 p-2 align-items-center" style={{border:'1px solid blue', borderRadius: '15px'}} >
                            <div style={{width: '70%'}}>{playlist.name}</div>
                            <Button style={{width: '30%'}} className="justify-content-end" variant="primary" onClick={()=>addVideoToPlaylistHandler(playlist.id, video.id)}>Add</Button>
                        </div>
                    )
                }):null}
            </div>
            </Modal>
            <ReportModal videoId={video.id} reportModalHandler={reportModalHandler} reportModal={reportModal}/>
            <PrivateVideoPasswordModal privateVideoModalOpen={privateVideoModalOpen} privatePassword={video.privatePassword}/>
        </div>
    );
};

export default VideoDetails;