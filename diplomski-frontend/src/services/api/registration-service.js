import axios from "axios";

const basicApiUrl = "http://localhost:8080/api/user"

const register = (newUser) =>{
    return axios
        .post(basicApiUrl + "/register", newUser)
        .then(response =>{
            return response;
        });
}

const registrationService = {
    register
}

export default registrationService;