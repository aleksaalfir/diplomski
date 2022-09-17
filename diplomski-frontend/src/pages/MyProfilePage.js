import React from 'react';
import StandardLayout from "../components/layouts/StandardLayout";
import MyProfile from "../components/Profile/MyProfile";

const MyProfilePage = (props) => {
    return (
        <StandardLayout>
            <MyProfile/>
        </StandardLayout>
    );
};

export default MyProfilePage;