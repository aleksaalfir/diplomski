import React, {useEffect, useState} from 'react';
import userService from "../../services/api/user-service";
import MyProfileVideos from "./MyProfileVideos";
import MyProfileInfo from "./MyProfileInfo";

const today = new Date();

const MyProfile = (props) => {

    const [channelInfo, setChannelInfo] = useState({channelName: "",
                                                            subscriptionNumber: "",
                                                            id: "",
                                                            firstname: "",
                                                            lastName: "",
                                                            username: "",
                                                            email: "", birthDate: today.toISOString().slice(0,10)});
    const [videoList, setVideoList] = useState([]);

    useEffect(()=>{
        userService.getMyProfile()
            .then(data => {
                setChannelInfo(data.channel);
                setVideoList(data.videos);
            })
    }, []);

    return (
        <div className="d-flex flex-column mt-2 flex-wrap" style={{marginBottom: "265px"}}>
            <MyProfileInfo channelInfo={channelInfo}/>
            <MyProfileVideos videoList={videoList}/>
        </div>
    );
};

export default MyProfile;