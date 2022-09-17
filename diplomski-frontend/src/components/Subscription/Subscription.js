import React, {useEffect, useState} from 'react';
import SubscriptionAbout from "./SubscriptionAbout";
import subscriptionService from "../../services/api/subscription-service";
import VideoCard from "../Videos/VideoCard";

const Subscription = () => {

    const [videos, setVideos] = useState([]);

    useEffect(()=>{
        subscriptionService.getSubscriptionVideos().then(response=>{
            console.log("DATA")
            console.log(response)
            setVideos(response.data);
        })
    }, [])

    return (
        <div className="mx-auto w-75">
            <SubscriptionAbout />
            <hr/>
            <div className=" w-70 mt-4 d-flex flex-wrap flex-row justify-content-start">
                {videos.length > 0 ? videos.sort((a,b)=> new Date(b.date).getTime() - new Date(a.date).getTime()).map((video, index) =>{
                    return (
                        <VideoCard videoPath={video.videoPath}
                                   ageRestricted={video.ageRestricted}
                                   id={video.id}
                                   name={video.name}
                                   date={video.date}
                                   views={video.views}
                                   key={index}
                                   channelName={video.user.channelName}
                                   userId={video.user.id}
                                   marginRight="me-3"/>
                    )
                }) : <h6 style={{marginBottom: '23.7rem'}}>You don't have any subscriptions.</h6>}
            </div>
        </div>
    );
};

export default Subscription;