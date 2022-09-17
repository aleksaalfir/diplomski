import React, {useEffect, useState} from 'react';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faMusic} from "@fortawesome/free-solid-svg-icons";
import PlaylistCard from "./PlaylistCard";
import {Button} from "react-bootstrap";
import Modal from 'react-modal';
import PlaylistService from "../../services/api/playlist-service";
import Swal from "sweetalert2";
import videos from "../Videos/Videos";
import {useNavigate} from "react-router-dom";
import videoService from "../../services/api/video-service";

const customStyles = {
    overlay: {
        backgroundColor: 'rgba(119,119,119,0.7)'
    },
    content: {
        top: '50%',
        left: '50%',
        right: 'auto',
        bottom: 'auto',
        marginRight: '-50%',
        transform: 'translate(-50%, -50%)',

        boxShadow: '0 0 10px rgba(0,0,0,0.6)',
        MozBoxShadow: "0 0 10px rgba(0,0,0,0.6)",
        WebkitBoxShadow: "0 0 10px rgba(0,0,0,0.6)",
        OBoxShadow: "0 0 10px rgba(0,0,0,0.6)",
    },

};

const Playlists = () => {

    const navigate = useNavigate();
    const [modalIsOpen, setModalIsOpen] = useState(false);
    const [playlists, setPlaylists] = useState([]);
    const [playlist, setPlaylist] = useState({name: "", privatePlaylist: false})

    useEffect(()=>{
       PlaylistService.getPlaylists().then(data => {
           setPlaylists(data);
       });

    }, [])

    function modalStateHandler(){
        modalIsOpen ? setModalIsOpen(false) : setModalIsOpen(true)
    }

    const deletePlaylistHandler = (id) => {
        Swal.fire({
            icon: 'error',
            title: 'Do you really want to delete playlist?',
            showCancelButton: true,
            confirmButtonText: 'Delete',
        }).then((result) => {
            if (result.isConfirmed) {
                PlaylistService.deletePlaylist(id).then(data =>{
                    window.location.reload();
                }).catch(err => console.log(err));
            }
        })
    }

    const privateHandler = () =>{
        const opositeBoolean = !playlist.privatePlaylist;
        setPlaylist(playlist =>({...playlist, privatePlaylist: opositeBoolean }));
    }


    const createPlaylistHandler = () => {
        PlaylistService.create(playlist)
            .then(data=>{
                Swal.fire({
                    title: 'New playlist created!',
                    icon: 'success',
                    showConfirmButton: false,
                    timer: 1500
                }).then(()=>{
                    window.location.reload()
                });
            })

    }

    return (
        <div className="mx-auto w-75">
            <div className="mt-2">
                <h1>My playlists <FontAwesomeIcon icon={faMusic} /></h1>
                <Button variant="primary" onClick={modalStateHandler}>Create new</Button>
            </div>
            <hr/>
            <div className=" w-70 mt-4 d-flex flex-wrap flex-row justify-content-start">
                {playlists.length > 0 ? playlists.map(( playlist , index) =>{
                    return <PlaylistCard
                        key={index}
                        id={playlist.id}
                        videoCount={playlist.videos.length}
                        name={playlist.name}
                        privatePlaylist={playlist.privatePlaylist}
                        deletePlaylistHandler={deletePlaylistHandler}/>;
                }) : <h6 style={{marginBottom: '21.7rem'}}>You don't have any playlist.</h6>}
            </div>
            <Modal
                isOpen={modalIsOpen}
                onRequestClose={modalStateHandler}
                style={customStyles}
            >
                <h2 className="text-center mb-3">Create playlist</h2>
                <div>
                    <ul style={{listStyleType: "none"}}>
                        <li className="mb-3">
                            <label className="w-100" style={{fontWeight: "bold"}}>Playlist name</label>
                            <input className="w-75" type="text" name="name" value={playlist.name}
                                   onChange={(event)=>setPlaylist(playlist=>({...playlist, name: event.target.value}))}/>
                        </li>
                        <li className="mb-3">
                            <label  style={{fontWeight: "bold", marginRight: "10px"}}>Private</label>
                            <input  type="checkbox" name="privatePlaylist" checked={playlist.privatePlaylist}
                                    onChange={privateHandler}/>
                        </li>
                    </ul>
                    <div className="text-center">
                        <Button style={{marginRight: "5px"}} onClick={createPlaylistHandler}>Create</Button>
                        <Button variant="secondary" onClick={modalStateHandler}>Cancel</Button>
                    </div>
                </div>
            </Modal>
        </div>
    );
};

export default Playlists;