import React from 'react';
import StandardLayout from "../components/layouts/StandardLayout";
import Playlists from "../components/Playlists/Playlists";

const PlaylistsPage = () => {
    return (
        <StandardLayout>
            <Playlists/>
        </StandardLayout>
    );
};

export default PlaylistsPage;