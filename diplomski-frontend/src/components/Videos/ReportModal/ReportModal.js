import React, {useState} from 'react';
import Modal from "react-modal";
import reasonsJSON from './ReportReasons.json';
import {Button} from "react-bootstrap";
import ReportService from "../../../services/api/report-service";
import {useNavigate} from "react-router-dom";

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

const ReportModal = (props) => {
    const [report, setReport] = useState({reason: "", videoId: props.videoId});
    const navigate = useNavigate();

    const onChangeValue = event => {
        setReport(report=>({...report, reason: event.target.value, videoId: props.videoId}));
        console.log(report);
    }

    const submitHandler = () => {
      ReportService.save(report).then(data => {
          alert("Thank you for helping our community.")
          navigate("/home");
      })
    }

    return (
        <Modal
            isOpen={props.reportModal}
            onRequestClose={props.reportModalHandler}
            style={customStyles}
            ariaHideApp={false}
        >
            <div className="d-flex flex-column" >
                <h6 className="text-center fs-5 mb-3">Report video</h6>
                <div className="" onChange={onChangeValue}>
                    {reasonsJSON.map(reason=>{
                        return (
                            <div className="mb-3 d-flex p-1 align-items-center" key={reason.id}>
                                <input  type="radio" value={reason.reason} name="reason"/>
                                <label className="ms-1">{reason.reason}</label>
                                <br></br>
                            </div>
                        )
                    })}
                </div>
                <div className="d-flex justify-content-evenly">
                    <Button className="bg-danger" onClick={submitHandler}>Report</Button>
                    <Button className="bg-secondary" onClick={props.reportModalHandler}>Cancel</Button>
                </div>
            </div>
        </Modal>
    );
};

export default ReportModal;