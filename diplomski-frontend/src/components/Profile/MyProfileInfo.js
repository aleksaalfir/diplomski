import React, {useEffect, useState} from 'react';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faCircleUser} from "@fortawesome/free-solid-svg-icons";
import styles from "./Profile.module.css";
import {Button} from "react-bootstrap";
import Modal from "react-modal";
import UserService from "../../services/api/user-service";

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

const MyProfileInfo = (props) => {
    const [modalIsOpen, setModalIsOpen] = useState(false);
    const [channelInfo, setChannelInfo] = useState({channelName: props.channelInfo.channelName,
        id: props.channelInfo.id,
        firstName: props.channelInfo.firstName,
        lastName: props.channelInfo.lastName,
        username: props.channelInfo.username,
        email: props.channelInfo.email,
        birthDate: props.channelInfo.birthDate,
        subscriptionNumber: props.channelInfo.subscriptionNumber});

    useEffect(()=>{
        setChannelInfo(props.channelInfo);
    }, [props.channelInfo])

    function modalStateHandler(){
        modalIsOpen ? setModalIsOpen(false) : setModalIsOpen(true)
    }

    const updateHandler = () => {
      UserService.update(channelInfo).then(response=>window.location.reload());
    }

    return (
        <>
        <div className="d-flex flex-row flex-wrap" style={{height: '230px'}}>
            <div className="d-flex flex-row align-items-center"  style={{background:'white',  width: '100%'}}>
                <div style={{width: "20%"}}>
                    <FontAwesomeIcon style={{width: "100%", height: "50%", color: 'gray'}} icon={faCircleUser} />
                </div>
                <div style={{width: '80%', marginLeft: '1rem', background: 'white'}}>
                    <div >
                        <p className={styles.channelName}>{channelInfo.channelName}</p>
                        <p className={styles.subscribeInfo}>{channelInfo.subscriptionNumber} subscribers</p>
                    </div>
                    <div>
                        <Button variant="secondary" onClick={modalStateHandler}>Edit</Button>
                    </div>
                </div>
            </div>
        </div>
        <Modal
            isOpen={modalIsOpen}
            onRequestClose={modalStateHandler}
            style={customStyles}
            ariaHideApp={false}
        >
            <h2 className="text-center mb-3">Personal information's</h2>
            <div >
                <ul style={{listStyleType: "none"}}>
                    <li className="mb-3">
                        <label className="w-100" style={{fontWeight: "bold"}}>First name</label>
                        <input className="w-75" type="text" name="firstName" value={channelInfo.firstName}
                               onChange={(event)=>setChannelInfo(channelInfo=>({...channelInfo, firstName: event.target.value}))}/>
                    </li>
                    <li className="mb-3">
                        <label className="w-100" style={{fontWeight: "bold"}}>Last name</label>
                        <input className="w-75" type="text" name="lastName" value={channelInfo.lastName}
                               onChange={(event)=>setChannelInfo(channelInfo=>({...channelInfo, lastName: event.target.value}))}/>
                    </li>
                    <li className="mb-3">
                        <label className="w-100" style={{fontWeight: "bold"}}>Birth date</label>
                        <input className="w-75" type="date" name="birthDate" value={new Date(channelInfo.birthDate).toISOString().slice(0,10)}
                               onChange={(event)=>setChannelInfo(channelInfo=>({...channelInfo, birthDate: event.target.value}))}/>
                    </li>
                    <li className="mb-3">
                        <label className="w-100" style={{fontWeight: "bold"}}>Channel name</label>
                        <input className="w-75" type="text" name="channelName" value={channelInfo.channelName}
                               onChange={(event)=>setChannelInfo(channelInfo=>({...channelInfo, channelName: event.target.value}))}/>
                    </li>
                </ul>
                <div className="ms-4 mb-3">
                    Other info:
                    <div>Username: {channelInfo.username}</div>
                    <div>Email: {channelInfo.email}</div>
                </div>
                <div className="text-center">
                    <Button style={{marginRight: "5px"}} onClick={updateHandler}>Update</Button>
                    <Button variant="secondary" onClick={modalStateHandler}>Cancel</Button>
                </div>
            </div>
        </Modal>
        </>
    );
};

export default MyProfileInfo;