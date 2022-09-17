import axios from "axios";

const baseUrl = "http://localhost:8080/api/subscribe"

const getSubscriptionVideos = () => {
  return axios.get(baseUrl).then(res=>res);
}

const subscribe = (id) =>{
  return axios.get(baseUrl + "/" + id).then(res=>res.data);
}

const SubscriptionService = {
  getSubscriptionVideos,
  subscribe
}

export default SubscriptionService;