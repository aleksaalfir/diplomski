import React from 'react';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faClockRotateLeft, faTrash} from "@fortawesome/free-solid-svg-icons";
import styles from "./WatchHistory.module.css";

const WatchHistoryAbout = (props) => {
    return (
        <div className="mt-2">
            <h1>Watch history <FontAwesomeIcon  icon={faClockRotateLeft} /></h1>
            <p className={styles.delete} onClick={props.deleteWatchHistoryHandler}>Delete your watch history <FontAwesomeIcon  icon={faTrash}/></p>
        </div>
    );
};

export default WatchHistoryAbout;