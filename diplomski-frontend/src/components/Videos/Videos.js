import React, {useEffect, useState} from 'react';
import videoService from "../../services/api/video-service";
import VideoCard from "./VideoCard";
import {useLocation} from "react-router-dom";

const Videos = () => {

    const [videos, setVideos] = useState([]);
    const { search } = useLocation();
    const searchParams = new URLSearchParams(search);
    const searchQuery = searchParams.get('search');
    const [sort, setSort] = useState("");

    useEffect( () => {
        videoService.getAll(searchQuery).then(data=>{
            setVideos(data.sort(() => Math.random() - 0.5));
        })
        return () => {}
    }, [searchQuery]);

    function sortVideos(value) {
        setSort(value);
        if(value === "mostViewed") setVideos(videos.sort((a,b)=> b.views - a.views));
        if(value === "lessViewed") setVideos(videos.sort((a,b)=> a.views - b.views));
        if(value === "newer") setVideos(videos.sort((a,b)=> new Date(b.date).getTime() - new Date(a.date).getTime()));
        if(value === "older") setVideos(videos.sort((a,b)=> new Date(a.date).getTime() - new Date(b.date).getTime()));
    }

    return (
        <div>
            <div className="mt-3">
                <label onClick={()=>console.log(sort)} htmlFor="sort" className="me-1">Sort by:</label>

                <select name="sort" id="sort"
                        value={sort}
                        onChange={(e)=>sortVideos(e.target.value)}>
                    <option value="" disabled>Choose option</option>
                    <option value="newer">Newer</option>
                    <option value="older">Older</option>
                    <option value="mostViewed">Most viewed</option>
                    <option value="lessViewed">Less viewed</option>
                </select>
            </div>
            <div className="mx-auto w-70 mt-3 d-flex flex-wrap flex-row justify-content-start">
                {
                    videos.map((video, index) =>{
                        return <VideoCard id={video.id} marginRight="me-4" ageRestricted={video.ageRestricted} videoPath={video.videoPath} name={video.name} date={video.date} views={video.views} channelName={video.user.channelName} key={index} userId={video.user.id}/>
                    })
                }
            </div>
        </div>

    );
};

export default Videos;