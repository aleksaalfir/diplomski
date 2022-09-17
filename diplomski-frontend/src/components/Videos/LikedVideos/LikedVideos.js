import React, {useEffect, useState} from 'react';
import LikedVideosAbout from "./LikedVideosAbout";
import LikedVideosService from "../../../services/api/liked-videos-service";
import VideoCard from "../VideoCard";

const LikedVideos = () => {

    const [videos,setVideos] = useState([]);

    useEffect(()=>{
        LikedVideosService.getAllByLoggedUser().then(data=>setVideos(data));
    },[]);

    return (
        <div className="mx-auto w-75">
            <LikedVideosAbout/>
            <hr/>
            <div className=" w-70 mt-4 d-flex flex-wrap flex-row justify-content-start">
                {videos.length > 0 ? videos.map((video, index) =>{
                    return (
                        <VideoCard videoPath={video.videoPath}
                                   ageRestricted={video.ageRestricted}
                                   id={video.id}
                                   name={video.name}
                                   date={video.date}
                                   views={video.views}
                                   key={index}
                                   userId={video.user.id}
                                   marginRight="me-3"/>
                    )
                }) : <h6 style={{marginBottom: '23.7rem'}}>You don't have any liked videos.</h6>}
            </div>
            
        </div>
    );
};

export default LikedVideos;