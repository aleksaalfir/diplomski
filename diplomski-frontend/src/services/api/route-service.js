import {Navigate, Route} from 'react-router-dom';
import MainPage from "../../pages/MainPage";
import Registration from "../../components/Registration/Registration";
import LoginPage from "../../pages/LoginPage";
import VideoDetailsPage from "../../pages/VideoDetailsPage";
import PlaylistInfoPage from "../../pages/PlaylistInfoPage";
import ProfilePage from "../../pages/ProfilePage";
import MyProfilePage from "../../pages/MyProfilePage";
import PlaylistsPage from "../../pages/PlaylistsPage";
import WatchHistoryPage from "../../pages/WatchHistoryPage";
import ReportsPage from "../../pages/ReportsPage";
import authService from "./auth-service";
import AddVideoPage from "../../pages/AddVideoPage";
import LikedVideosPage from "../../pages/LikedVideosPage";
import SubscriptionPage from "../../pages/SubscriptionPage";

class RouteService {

    openRoutes=[
        {path: "/home", element: <MainPage/>},
        {path: "/registration", element: <Registration/>},
        {path: "/login", element: <LoginPage/>},
        {path: "/video/:id", element: <VideoDetailsPage/>},
        {path: "/playlist/:id", element: <PlaylistInfoPage/>},
        {path: "/playlists/channel/:id", element: <ProfilePage playlistcheck={true}/>},
        {path: "/videos/channel/:id", element: <ProfilePage playlistcheck={false}/>},
        {path: "/channel/:id", element: <ProfilePage playlistcheck={false}/>}
    ]

    userRoutes=[
        {path: "/home", element: <MainPage/>},
        {path: "/video/:id", element: <VideoDetailsPage/>},
        {path: "/playlist/:id", element: <PlaylistInfoPage/>},
        {path: "/playlists/channel/:id", element: <ProfilePage playlistcheck={true}/>},
        {path: "/videos/channel/:id", element: <ProfilePage playlistcheck={false}/>},
        {path: "/channel/:id", element: <ProfilePage playlistcheck={false}/>},
        {path: "/profile", element: <MyProfilePage/>},
        {path: "/playlists", element: <PlaylistsPage/>},
        {path: "/watch-history", element: <WatchHistoryPage/>},
        {path: "/add-video", element: <AddVideoPage/>},
        {path: "/liked-videos", element: <LikedVideosPage/>},
        {path: "/subscription", element: <SubscriptionPage/>}
    ]

    adminRoutes = [
        {path: "/reports", element: <ReportsPage/>},
        {path: "/video/:id", element: <VideoDetailsPage/>}
    ]

    getAllowedRoutes = () =>{
        let role = authService.getRoleFromJwt();
        if(role === "ROLE_USER")
            return this.userRoutes;

        if(role === "ROLE_ADMINISTRATOR")
            return this.adminRoutes;

        return this.openRoutes;
    }

    getRedirect = () =>{
        const role = authService.getRoleFromJwt();

        if (role === "ROLE_USER")
            return <Route path="*" element={<Navigate to ="/home" />}/>

        if (role === "ROLE_ADMINISTRATOR")
            return <Route path="*" element={<Navigate to ="/reports" />}/>

        return <Route path="*" element={<Navigate to ="/login" />}/>
    }

}

export default new RouteService();