import React, {useState} from 'react';
import {Button} from "react-bootstrap";
import Swal from "sweetalert2";
import registrationService from "../../services/api/registration-service";
import {useNavigate} from "react-router-dom";

const today = new Date();

const Registration = () => {

    const [newUser, setNewUser] = useState({firstName: "", lastName: "", email: "", username: "", birthDate: today.toISOString().slice(0,10), channelName: "", password: ""});
    const [repeatPassword, setRepeatPassword] = useState("");
    const navigate = useNavigate();

    const submitHandler = (event) =>{
        event.preventDefault();
        if (newUser.firstName.trim() === ""){
            errorMessage("First name is empty!")
            return;
        }
        if(newUser.lastName.trim() === ""){
            errorMessage("Last name is empty!")
            return;
        }
        if(newUser.email.trim() === ""){
            errorMessage("Email is empty!")
            return;
        }
        if(newUser.username.trim() === ""){
            errorMessage("Username is empty!")
            return;
        }
        if(newUser.channelName.trim() === ""){
            errorMessage("Channel name is empty!")
            return;
        }
        if(newUser.password.trim() === ""){
            errorMessage("Password is empty!")
            return;
        }
        if(repeatPassword.trim() === ""){
            errorMessage("Repeated password is empty!")
            return;
        }
        if(newUser.password.trim() !== repeatPassword.trim()){
            errorMessage("Password and repeated password need to match!")
            return;
        }

        registrationService.register(newUser).then(response =>{
            Swal.fire({
                position: 'center',
                icon: 'success',
                title: 'You have registered successfully!',
                showConfirmButton: false,
                timer: 2000
            }).then(res=> navigate("/login"))
        }).catch(err =>{
            if(err.response.status === 409) {
                errorMessage("Username or email already taken. Try new!")
            }
        })
    }

    const changeHandler = (event, prop) => {
        setNewUser({...newUser, [prop]: event.target.value});
    }

    const errorMessage = (text) => {
        return Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: text,
            confirmButtonText: "Try again"
        })
    }

    return (
        <div className="w-50 mx-auto">
                <h1 className="text-center mt-3">Registration</h1>
                <div className="form-group my-3">
                    <label htmlFor="firstName" className="form-control-label">First name</label>
                    <input id="firstName" className="form-control" name="firstName" type="text" placeholder="John..." value={newUser.firstName} onChange={event => changeHandler(event,"firstName")}/>
                </div>
                <div className="form-group my-3">
                    <label htmlFor="lastName" className="form-control-label">Last name</label>
                    <input id="lastName" className="form-control" name="lastName" type="text" placeholder="Winston..." value={newUser.lastName} onChange={event => changeHandler(event,"lastName")}/>
                </div>
                <div className="form-group my-3">
                    <label htmlFor="email" className="form-control-label">Email</label>
                    <input id="email" className="form-control" name="email" type="text" placeholder="johnwinston@mail.com..." value={newUser.emai} onChange={event => changeHandler(event,"email")}/>
                </div>
                <div className="form-group my-3">
                    <label htmlFor="birthDate" className="form-control-label">Birth date</label>
                    <input id="birthDate" className="form-control" name="birthDate" type="date" value={newUser.birthDate} onChange={event => changeHandler(event,"birthDate")}/>
                </div>
                <div className="form-group my-3">
                    <label htmlFor="channelName" className="form-control-label">Channel name</label>
                    <input id="channelName" className="form-control" name="channelName" type="text" placeholder="Johny's videos..." value={newUser.channelName} onChange={event => changeHandler(event,"channelName")}/>
                </div>
                <div className="form-group my-3">
                    <label htmlFor="username" className="form-control-label">Username</label>
                    <input id="username" className="form-control" name="username" type="text" placeholder="johny..." value={newUser.username} onChange={event => changeHandler(event,"username")}/>
                </div>
                <div className="form-group my-3">
                    <label htmlFor="password" className="form-control-label">Password</label>
                    <input id="password" className="form-control" name="password" type="password" value={newUser.password} onChange={event => changeHandler(event,"password")}/>
                </div>
                <div className="form-group my-3">
                    <label htmlFor="repeatPassword" className="form-control-label">Repeat your password</label>
                    <input id="repeatPassword" className="form-control" name="repeatPassword" type="password" value={repeatPassword} onChange={event => setRepeatPassword(event.target.value)}/>
                </div>
                <div className="form-group my-3 text-center">
                    <Button type="submit" variant="primary" onClick={submitHandler}>Register</Button>
                </div>
        </div>
    );
};

export default Registration;