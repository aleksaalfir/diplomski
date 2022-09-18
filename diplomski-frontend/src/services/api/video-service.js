import axios from "axios";

const baseUrl = "http://localhost:8080/api/videos"

const getAll = searchQuery => {
  return axios.get(baseUrl + "?search=" + searchQuery)
      .then(response => response.data)
}

const getOne = (id) => {
  return axios
      .get(baseUrl + "/" + id)
      .then(response => response.data);
}

const comment = (comment) =>{
  return axios.post("http://localhost:8080/api/video-comment", comment)
      .then(response => response);
}

const deleteVideo = (id) =>{
  return axios.delete(baseUrl + "/" + id)
      .then(response => response.data);
}

const update = (id, video) =>{
  return axios.put(baseUrl + "/" + id, video)
      .then(response=>response.data);
}

const create = (video) => {
  return axios.post(baseUrl, video)
      .then(res=>res.data);
}

const like = (id) => {
  return axios.get(baseUrl + "/like/" +id)
      .then(res => res.data)
}

const dislike = (id) => {
  return axios.get(baseUrl + "/dislike/" +id)
      .then(res => res.data)
}

const videoService = {
  getAll,
  getOne,
  comment,
  deleteVideo,
  update,
  create,
  like,
  dislike
}

export default videoService;