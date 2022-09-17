import React, {useState} from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import {faVideo} from "@fortawesome/free-solid-svg-icons";
import {Button, Form, InputGroup} from "react-bootstrap";
import {Link, useNavigate} from "react-router-dom";
import axios from "axios";
import Swal from "sweetalert2";
import authService from "../services/api/auth-service";

const LoginPage = () => {

    const [logData, setLogData] = useState({username: "", password: ""})
    const navigate = useNavigate();

    const usernameChangeHandler = (event) =>{
            setLogData(logData=>({...logData, username: event.target.value}));
    }

    const passwordChangeHandler = (event) =>{
        setLogData(logData=>({...logData, password: event.target.value}));
    }

    const submitLoginHandler = () =>{
        axios.post("http://localhost:8080/api/auth/login", logData)
            .then(res=>{
                authService.login(res.data.token);
                navigate("/home");
            })
            .catch(err=>{
                Swal.fire({
                    position: 'center',
                    icon: 'error',
                    title: 'Wrong username or password!',
                    showConfirmButton: false,
                    timer: 7000
                })
            })
    }

    return (
        <div className="d-flex justify-content-center" style={{height: '100vh'}}>
            <div className="w-50 justify-content-center align-items-center bg-danger d-flex flex-column" style={{color: 'white'}}>
                <div><FontAwesomeIcon style={{fontSize: '10rem'}} icon={faVideo}/></div>
                <h1>Welcome to VideoTube!</h1>
                <small>Best place for videos</small>
            </div>
            <div className="w-50 justify-content-center align-items-center d-flex flex-column">
                <h1 className="mb-4">Log in</h1>
                <div className="text-center w-75 mx-auto" style={{border: '1px solid red', borderRadius: '3rem'}}>
                    <Form.Label htmlFor="username" style={{fontSize: '1.5rem'}}>Username</Form.Label>
                    <InputGroup className="mb-3 w-75 mx-auto">
                        <Form.Control placeholder="username..." id="username" onChange={(e)=>usernameChangeHandler(e)} aria-describedby="basic-addon3" />
                    </InputGroup>

                    <Form.Label htmlFor="password" style={{fontSize: '1.5rem'}}>Password</Form.Label>
                    <InputGroup className="mb-3 w-75 mx-auto">
                        <Form.Control type="password" placeholder="password..." id="password" onChange={(e)=>passwordChangeHandler(e)} aria-describedby="basic-addon3" />
                    </InputGroup>
                    <Button className="p-2 mb-2" variant="danger" onClick={submitLoginHandler}>Log in</Button>
                </div>
                <div className="mt-2">Don't have an account? <Link to={`/registration`}>Register now</Link></div>

            </div>
        </div>
    );
};

export default LoginPage;