import React from 'react';
import StandardLayout from "../components/layouts/StandardLayout";
import LikedVideos from "../components/Videos/LikedVideos/LikedVideos";

const LikedVideosPage = () => {
    return (
        <StandardLayout>
            <LikedVideos/>
        </StandardLayout>
    );
};

export default LikedVideosPage;