import React from 'react';
import VideoCard from "./VideoCard";

const VideoDetailsVideos = (props) => {

    return (
        <div  className="d-flex flex-column align-items-center flex-wrap" style={{width:'25%',background:'lightgray', borderRadius: '40px', marginTop: '1.5rem'}}>
            <div style={{fontSize: '1.5rem', marginBottom: '5px'}}>More videos</div>
            {props.videos.filter(v=>v.id !== props.videoId).map((video , index) =>{
                return <VideoCard videoPath={video.videoPath}
                                  id={video.id}
                                  name={video.name}
                                  date={video.date}
                                  views={video.views}
                                  key={index}
                                  ageRestricted={video.ageRestricted}
                                  channelName={video.user.channelName}
                                  userId={video.user.id}/>
            })}
        </div>
    );
};

export default VideoDetailsVideos;