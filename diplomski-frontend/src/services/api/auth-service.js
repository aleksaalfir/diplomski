import jwt_decode from "jwt-decode";

class AuthService {

    getRoleFromJwt = () => {
        let decodedJwt = this.getDecodedJwt();
        if(decodedJwt == null)
            return null;

        return decodedJwt.role;
    }

    getUsernameFromJwt = () => {
        let decodedJwt = this.getDecodedJwt();
        if(decodedJwt == null)
            return null;

        return decodedJwt.sub;
    }

    getDecodedJwt = () => {
        let token = localStorage.getItem("token");
        if(token == null)
            return null;

        return jwt_decode(token);
    }

    isUser = () => {
        return this.getRoleFromJwt() === "ROLE_USER";
    }

    isAdministrator = () => {
        return this.getRoleFromJwt() === "ROLE_ADMINISTRATOR";
    }

    isLoggedIn = () => {
        return this.getDecodedJwt() !== null;
    }

    login = (token) => {
        localStorage.setItem("token", token);
        window.location.replace("/home");
    }

    logout = () => {
        localStorage.clear();
        window.location.replace("/home");
    }
}

export default new AuthService();