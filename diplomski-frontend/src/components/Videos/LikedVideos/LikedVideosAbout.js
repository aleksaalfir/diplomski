import React from 'react';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faThumbsUp} from "@fortawesome/free-solid-svg-icons";
import styles from "../../WatchHistory/WatchHistory.module.css";

const LikedVideosAbout = () => {
    return (
        <div className="mt-2">
            <h1>Liked videos <FontAwesomeIcon  icon={faThumbsUp} /></h1>
        </div>
    );
};

export default LikedVideosAbout;