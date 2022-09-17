import React from 'react';
import Navbar from '../Navigation/Navigation'
import Footer from '../Footer/Footer'
import {Container} from "react-bootstrap";

const StandardLayout = (props) => {
    return (
        <>
            <Navbar/>
            <Container fluid={props.fluid} className={props.fluid && "w-75 mx-auto"}>
                <main style={{marginBottom:"8rem"}}>
                    {props.children}
                </main>
            </Container>
            <Footer/>
        </>

    );
};

export default StandardLayout;