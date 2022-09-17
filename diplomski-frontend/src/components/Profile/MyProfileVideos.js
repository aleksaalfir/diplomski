import React from 'react';
import VideoCard from "../Videos/VideoCard";

const MyProfileVideos = (props) => {

    let videos = props.videoList.map((video , index) =>{
        return <VideoCard videoPath={video.videoPath}
                          id={video.id}
                          name={video.name}
                          date={video.date}
                          views={video.views}
                          key={index}
                          marginRight={"me-3"}
                          ageRestricted={video.ageRestricted}
                          userId={video.user.id}/>
    })

    if(videos.length === 0){
        videos = <h5>There are no videos on this channel.</h5>
    }

    return (
        <div>
            <div style={{marginTop: '3rem'}} >
                <h2>My videos</h2>
            </div>
            <div className=" w-70 mt-4 d-flex flex-wrap flex-row justify-content-start">
                {videos}
            </div>
        </div>
    );
};

export default MyProfileVideos;