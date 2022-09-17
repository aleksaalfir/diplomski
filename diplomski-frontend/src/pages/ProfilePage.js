import React from 'react';
import StandardLayout from "../components/layouts/StandardLayout";
import Profile from "../components/Profile/Profile";


const ProfilePage = ( props) => {
    return (
        <StandardLayout>
            <Profile playlistcheck={props.playlistcheck}/>
        </StandardLayout>
    );
};

export default ProfilePage;