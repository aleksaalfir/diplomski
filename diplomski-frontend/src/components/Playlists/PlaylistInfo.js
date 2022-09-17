import React, {useEffect, useState} from 'react';
import {Link, useParams} from "react-router-dom";
import playlistService from "../../services/api/playlist-service";
import VideoCard from "../Videos/VideoCard";

const PlaylistInfo = () => {

    const {id} = useParams();
    const [channelInfo, setChannelInfo] = useState({channelName: "", subscriptionNumber: "", id: ""});
    const [playlistInfo, setPlaylistInfo] = useState({id: "", name: "", videos: {}, privatePlaylist: false});

    useEffect(()=>{
        playlistService.getPlaylist(id)
            .then(data =>{
                setChannelInfo(data.channel);
                setPlaylistInfo(data.playlist);
            })
    }, [id])

    return (
        <div className="mx-auto w-75">
            <div className="mt-2">
                <h1>{playlistInfo.name} {playlistInfo.privatePlaylist ? "(private playlist)" : null}</h1>
                    <h4>Creator:
                        <Link  className="ms-2" style={{textDecoration: "none", color: "black"}} to={`/channel/${channelInfo.id}`}>
                            {channelInfo.channelName}
                        </Link>
                    </h4>
                <p>{playlistInfo.videos.length} videos</p>
            </div>
            <hr/>
            <div className=" w-70 mt-4 d-flex flex-wrap flex-row justify-content-start">
                {playlistInfo.videos.length > 0 ? playlistInfo.videos.map((video, index) =>{
                    return (
                        <VideoCard videoPath={video.videoPath}
                                   id={video.id}
                                   name={video.name}
                                   date={video.date}
                                   views={video.views}
                                   key={index}
                                   marginRight="me-3"
                                   userId={video.user.id}/>
                    )
                }) : <h6>There are no videos in this playlist.</h6>}
            </div>

        </div>
    );
};

export default PlaylistInfo;