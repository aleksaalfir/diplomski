import axios from "axios";
const baseUrl = "http://localhost:8080/api/liked-videos"

function getAllByLoggedUser() {
    return axios.get(baseUrl)
        .then(res=>res.data);
}

const LikedVideosService = {
    getAllByLoggedUser
}

export default LikedVideosService;