import React from 'react';
import {Card} from "react-bootstrap";
import moment from "moment";
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome'
import {faEye} from "@fortawesome/free-solid-svg-icons";
import {Link, useNavigate} from "react-router-dom";
import styles from './VideoCard.module.css'
import ageRestrictedPng from '../../assets/agerestricted.jpg'
import Swal from "sweetalert2";

const VideoCard = (props) => {

    const navigate = useNavigate();

    const popUp = () => {
        if(props.ageRestricted){
            Swal.fire({
                title: 'Sensitive video',
                text: 'Video content is sensitive. Do you want to continue?',
                showCancelButton: true,
                confirmButtonText: 'Continue',
            }).then((result) => {
                if (result.isConfirmed){
                    navigate("/video/" + props.id);
                }
            })
        }
    }

    function truncate(str, n){
        return (str.length > n) ? str.slice(0, n-1) + '...' : str;
    };

    return (
        <Card onClick={popUp} style={{ width: '18rem', marginBottom: '2rem', marginRight: '0rem'}} className={props.marginRight} border="danger">
            <Link to={props.ageRestricted ? "" : "/video/" + props.id} style={{textDecoration: "none"}}>
                {props.ageRestricted ?
                    <Card.Img style={{width:'100%', height: '25vh', objectFit: 'contain', backgroundColor: 'black'}} src={ageRestrictedPng}/>
                    :
                    <video key={Date.now()} preload="metadata" className="card-img-top" style={{width:'100%', height: '25vh', objectFit: 'contain', backgroundColor: 'black'}}>
                        {/*<source src={require(props.videoPath)}/>*/}
                        <source src={[props.videoPath,'#t=9.0'].join()}/>
                        {/*Your browser does not support the video tag.*/}
                    </video>}

            <Card.Body>
                <Card.Title style={{color: "black"}}>{truncate(props.name, 26)}</Card.Title>
                <Card.Subtitle className="mb-2 text-muted">{moment.utc(props.date).local().startOf('seconds').fromNow()} <FontAwesomeIcon icon={faEye} /> {props.views}</Card.Subtitle>
                {props.channelName ?
                    <Card.Text>
                        <Link to={`/channel/${props.userId}`} className={styles.link}>
                            {props.channelName}
                        </Link>
                    </Card.Text>
                    : null}

            </Card.Body>
            </Link>
        </Card>
    );
};

export default VideoCard;