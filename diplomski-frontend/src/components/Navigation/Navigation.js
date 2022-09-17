import React, {useState} from 'react';
import Container from 'react-bootstrap/Container';
import Navbar from 'react-bootstrap/Navbar';
import Nav from 'react-bootstrap/Nav';
import styles from './Navigation.module.css';
import {Button} from "react-bootstrap";
import {Link, useNavigate} from "react-router-dom";
import navbarService from "../../services/api/navbar-service";
import authService from "../../services/api/auth-service";

const Navigation = () => {

    const navigate = useNavigate();
    const [search, setSearchInput] = useState("");

    const navLinks = navbarService.getAllowedNavbarLinks().map((navLink, index)=>{
        return(
            <Link key={index} to={navLink.url} className={styles.link}>
                {navLink.text}
            </Link>
        )
    })

    const searchFunction = (event) =>{
        event.preventDefault();
        if(search !== ""){
            navigate("/home?search=" + search.trim())
        }else{
            navigate("/home")
        }
    }

    return (
        <Navbar collapseOnSelect expand="lg" className="text-white m-0" style={{backgroundColor: '#F93154'}}>
            <Container>
                <div className="d-flex align-items-center" >
                    <Link to="/home" className={styles.brand} >
                        VideoTube
                    </Link>
                    <form onSubmit={event => searchFunction(event)} style={{width:'100%'}}>
                        <input className="ms-2" style={{borderRadius: '10px', width: '100%'}} placeholder="Search..." type='search' name="search" onChange={event => setSearchInput(event.target.value)}/>
                    </form>
                </div>
                <Navbar.Toggle aria-controls="responsive-navbar-nav" className={styles.toggle}/>
                <Navbar.Collapse id="responsive-navbar-nav" className="d-flex justify-content-end">
                    {authService.isUser() ? <Button onClick={()=>navigate('/add-video')} size="sm" variant="primary" >
                        Add video
                    </Button> : null}
                    <Nav className="ml-auto">
                        {navLinks}
                    </Nav>
                    {
                        authService.isLoggedIn() ?
                            <Button size="sm" variant="light" className={styles.link} onClick={()=>authService.logout()}>
                                Logout
                            </Button>
                            :
                            <Button size="sm" variant="light" className={styles.link} onClick={()=> navigate("/login")}>
                                Login
                            </Button>
                    }
                </Navbar.Collapse>
            </Container>
        </Navbar>
    );
}

export default Navigation;