import React, {useEffect, useState} from 'react';
import Modal from "react-modal";
import {Button} from "react-bootstrap";

const customStyles = {
    overlay: {
        backgroundColor: 'rgba(119,119,119,1)'
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

const PrivateVideoPasswordModal = (props) => {

    const [error, setError] = useState(false);
    const [inputPass, setInputPass] = useState("");
    const [modalOpen, setModalOpen] = useState(true);

    useEffect(()=>{
        setModalOpen(props.privateVideoModalOpen)
    }, [props.privateVideoModalOpen])

    const buttonHandler = () => {
      if(inputPass === props.privatePassword){

          setModalOpen(false)
      }else {
          setError(true);
      }
    }

    return (
        <Modal
            isOpen={modalOpen}
            shouldCloseOnOverlayClick={false}
            style={customStyles}
            ariaHideApp={false}
        >
            <div className="d-flex flex-column" style={{width: '20rem'}}>
                <h6>This video is private. Please enter password.</h6>
                <label htmlFor="password">Password:</label>
                <input type={"text"} name={"password"} id={"password"} onChange={(e)=>setInputPass(e.target.value)}/>
                <Button onClick={buttonHandler} style={{width: "5rem", marginTop: '5px'}}>Ok</Button>
                {error ? <strong style={{color: "red"}}>Password incorect!</strong> : null}
            </div>
        </Modal>
    );
};

export default PrivateVideoPasswordModal;