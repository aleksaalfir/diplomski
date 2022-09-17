import axios from "axios";

const baseUrl = "http://localhost:8080/api/report"

const save = (report) => {
  return axios.post(baseUrl, report)
      .then(res=>res.data);
}

function getAll() {
  return axios.get(baseUrl)
      .then(res=>res.data);
}

const accept = (id) => {
  return axios.delete(baseUrl + "/accept/" + id)
      .then(res => res);
}

const reject = (id) => {
  return axios.delete(baseUrl + "/reject/" + id)
      .then(res => res);
}

const ReportService = {
  save,
  getAll,
  accept,
  reject
}

export default ReportService;