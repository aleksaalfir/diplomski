import axios from "axios";

const baseUrl = "http://localhost:8080/api/watch-history";

const getOne = () =>{
    return axios.get(baseUrl)
        .then(res => res.data);
}

const deleteWatchHistory = () =>{
    return axios.delete(baseUrl)
        .then(res => res.data);
}

const WatchHistoryService = {
    getOne,
    deleteWatchHistory
}

export default WatchHistoryService