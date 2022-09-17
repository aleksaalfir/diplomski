import React, {useEffect, useState} from 'react';
import reportService from "../../services/api/report-service";
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome'
import {faFlag} from "@fortawesome/free-solid-svg-icons";
import {Button, Col, Container, Row} from "react-bootstrap";
import {Link} from "react-router-dom";

const Reports = () => {

    const [reports, setReports] = useState([]);

    useEffect(()=>{
        reportService.getAll().then(data=>{
            setReports(data)
        })
    }, [])

    const acceptHandler = (id) => {
      reportService.accept(id)
          .then(res => window.location.reload());
    }

    const rejectHandler = (id) => {
        reportService.reject(id)
            .then(res => window.location.reload());
    }

    return (
        <>
            <h1 className="mt-3">Reports <FontAwesomeIcon icon={faFlag}/></h1>
            <hr/>
            <Container>
                <Row style={{ marginBottom: '15px'}} className="text-center">
                    <Col className="fw-bold">Report ID</Col>
                    <Col className="fw-bold">Report reason</Col>
                    <Col className="fw-bold">Video ID</Col>
                    <Col className="fw-bold">Accept/Reject</Col>
                </Row>
                {reports.map((rep, index)=>{
                    return (
                        <Row className="text-center align-items-center" key={index} style={{ borderRadius: '15px',border: '2px solid red', marginBottom: '15px', padding: '10px'}}>
                            <Col>{rep.id}</Col>
                            <Col>{rep.reason}</Col>
                            <Col><Link to={`/video/${rep.videoId}`}>{rep.videoId}</Link></Col>
                            <Col>
                                <Button className="me-2" onClick={()=>acceptHandler(rep.id)}>Accept</Button>
                                <Button variant="danger" onClick={()=>rejectHandler(rep.id)}>Reject</Button>
                            </Col>
                        </Row>
                    )
                })}
            </Container>
        </>
    );
};

export default Reports;