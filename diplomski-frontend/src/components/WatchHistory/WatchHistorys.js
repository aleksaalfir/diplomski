import React, {useEffect, useState} from 'react';
import VideoCard from "../Videos/VideoCard";
import WatchHistoryService from "../../services/api/watch-history-service";
import WatchHistoryAbout from "./WatchHistoryAbout";

const WatchHistorys = () => {

    const [videos, setVideos] = useState([]);

    useEffect(() =>{
        WatchHistoryService.getOne().then(
            data => setVideos(data)
        );
    }, []);

    const deleteWatchHistoryHandler = () => {
      WatchHistoryService.deleteWatchHistory()
          .then(data => window.location.reload())
          .catch(err => console.log(err));
    }


    return (
        <div className="mx-auto w-75">
            <WatchHistoryAbout deleteWatchHistoryHandler={deleteWatchHistoryHandler}/>
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
                }) : <h6 style={{marginBottom: '21.7rem'}}>Watch history is empty.</h6>}
            </div>
        </div>
    );
};

export default WatchHistorys;