import axios from "axios";

const basicApiUrl = "http://localhost:8080/api/playlist"

const getPlaylist = (id) =>{
    return axios
        .get(basicApiUrl + `/${id}`)
        .then(response => response.data)
}

const getPlaylists = () =>{
    return axios
        .get(basicApiUrl)
        .then(response => response.data)
}

const deletePlaylist = (id) =>{
    return axios
        .delete(basicApiUrl + `/${id}`)
        .then(response => response.data)
}

function create(playlist) {
    return axios.post(basicApiUrl, playlist)
        .then(response => response.data);
}

const addVideoToPlaylist = (playlistId, videoId) => {
    return axios.put(basicApiUrl + `/${playlistId}/add-video/${videoId}`)
        .then(res=>res);
}

const playlistService = {
    getPlaylist,
    getPlaylists,
    deletePlaylist,
    create,
    addVideoToPlaylist
}

export default playlistService;