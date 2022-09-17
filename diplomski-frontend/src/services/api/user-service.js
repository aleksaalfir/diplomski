import axios from "axios";

const basicApiUrl = "http://localhost:8080/api/user"

const getChannelById = (id) => {
    return axios.get(basicApiUrl + `/channel/${id}`)
        .then(res => res.data);
};

const getMyProfile = () => {
    return axios.get(basicApiUrl + `/profile`)
        .then(res => res.data);
};

const update = (user) =>{
    return axios.put(basicApiUrl, user).then(response=>response);

}

const UserService = {
    getChannelById,
    getMyProfile,
    update
}

export default UserService;