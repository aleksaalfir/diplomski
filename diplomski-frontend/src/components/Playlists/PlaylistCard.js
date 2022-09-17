import React from 'react';
import {Button, Card} from "react-bootstrap";
import PlaylistImage from "../../assets/playlist.png"
import {Link} from "react-router-dom";
import styles from './PlaylistCard.module.css'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import {faLock} from "@fortawesome/free-solid-svg-icons";

const PlaylistCard = (props) => {

    return (

        <Card style={{ width: '18rem', marginBottom: '2rem', marginRight: '1rem'}} border="danger" className={styles.card}>
            {props.deletePlaylistHandler ?
                    <>
                    <Card.Img className="card-img-top" src={PlaylistImage} style={{padding: "10px 10px 0px 10px"}}/>
                    <Card.Body>
                        <Link to={`/playlist/${props.id}`} style={{textDecoration: "none"}}>
                            <Card.Title style={{color:"black"}}>{props.name} {props.privatePlaylist ? <FontAwesomeIcon icon={faLock}/> : null}</Card.Title>
                        </Link>
                        <Card.Subtitle className="mb-2 text-muted"> {props.videoCount} videos</Card.Subtitle>
                        <Button variant="danger" onClick={() => props.deletePlaylistHandler(props.id)}>Delete</Button>
                    </Card.Body>
                    </>
                    :
                <>
                <Link to={`/playlist/${props.id}`} style={{textDecoration: "none"}}>
                    <Card.Img className="card-img-top" src={PlaylistImage} style={{padding: "10px 10px 0px 10px"}}/>
                    <Card.Body>
                        <Card.Title style={{color:"black"}}>{props.name} {props.privatePlaylist ? <FontAwesomeIcon icon={faLock}/> : null}</Card.Title>
                        <Card.Subtitle className="mb-2 text-muted"> {props.videoCount} videos</Card.Subtitle>
                    </Card.Body>
                </Link>
                </>
            }

        </Card>

    );
};

export default PlaylistCard;