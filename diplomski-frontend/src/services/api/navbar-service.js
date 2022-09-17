import authService from "./auth-service";

class NavbarService{

    userLinks = [
        {url: "/profile", text: "Profile"},
        {url: "/playlists", text: "Playlists"},
        {url: "/watch-history", text: "Watch history"},
        {url: "/subscription", text: "Subscription"},
        {url: "/liked-videos", text: "Liked"},
    ]

    adminLinks = [
        {url: "/reports", text: "Reports"},
    ]

    getAllowedNavbarLinks = () =>{
        const role = authService.getRoleFromJwt();
        if(role === "ROLE_USER")
            return this.userLinks;
        else if (role === "ROLE_ADMINISTRATOR")
            return this.adminLinks;
        return [];
    }

}

export default new NavbarService();