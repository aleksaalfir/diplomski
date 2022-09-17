import React, {useState} from 'react';
import {Button} from "react-bootstrap";
import {storage} from "../../firebase";
import {getDownloadURL, ref, uploadBytes} from 'firebase/storage'
import authService from "../../services/api/auth-service";
import {v4} from 'uuid'
import videoService from "../../services/api/video-service";
import {useNavigate} from "react-router-dom";
import Swal from "sweetalert2";
import {PropagateLoader, PulseLoader} from "react-spinners";

const AddVideo = () => {

    const [video, setVideo] = useState({name: "", ageRestricted: false, privateVideo: false, description: "", videoPath: ""});
    const [videoFile, setVideoFile]= useState(null);
    const navigate = useNavigate();
    const [loading, setLoading] = useState(false);

    const privateHandler = () =>{
        const opositeBoolean = !video.privateVideo;
        setVideo(video =>({...video, privateVideo: opositeBoolean }));
    }

    const ageRestrictedHandler = () =>{
        const opositeBoolean = !video.ageRestricted;
        setVideo(video =>({...video, ageRestricted: opositeBoolean }));
    }

    const uploadHandler = () => {
        if(videoFile === null) return;
        //korisnik/uuid - putanja za videoRef
        //bekend saljem video naziv originalan, uuid je id videa, ...
        //id zameniti sa uuidom
        //dislajk
        //kad je privatan video korisnik ciji nije video bi trebao da ukuca sifru i dobije pristup video zapisu
        const videoRef = ref(storage, `videos/${authService.getUsernameFromJwt()}/${videoFile.name + v4()}`);
        setLoading(true);
        uploadBytes(videoRef, videoFile).then((snapshot)=>{
            getDownloadURL(snapshot.ref).then((url)=>{
                const sendVideo = {...video, videoPath: url}
                //dodaj catch ako pukne video
                videoService.create(sendVideo).then((id)=>{
                    setLoading(false);
                    navigate(`/video/${id}`)
                })
            })
        }).catch(err=> alert(`Oops error ${err.message}`))

    }

    return (
        <div className="mt-3 mx-auto w-50">
            <h1>Add video</h1>
            <div className="w-100 mb-3">
                <label htmlFor="name" style={{fontSize: '1.5rem', marginRight: '8px'}}>Video name:</label>
                <input type="text" name="name" id="name" className="w-50" onChange={event => setVideo({...video, name: event.target.value})}/>
            </div>
            <div className="w-100 mb-3">
                <label htmlFor="ageRestricted" className="d-inline" style={{fontSize: '1.5rem' , marginRight: '8px'}}>Age restriction:</label>
                <input type="checkbox"  name="ageRestricted" id="ageRestricted" onChange={ageRestrictedHandler} />
            </div>
            <div className="w-100 mb-3">
                <label htmlFor="privateVideo" style={{fontSize: '1.5rem', marginRight: '8px'}}>Private video:</label>
                <input type="checkbox" name="privateVideo" id="privateVideo" onChange={privateHandler}/>
            </div>
            <div className="w-100 mb-3">
                <label htmlFor="description" style={{fontSize: '1.5rem', verticalAlign: 'top', marginRight: '8px'}}>Description:</label>
                <textarea style={{verticalAlign: 'text-top'}} rows="5" cols="50" onChange={(event) => setVideo(video =>({...video, description: event.target.value}))}/>
            </div>
            <div className="w-100 mb-3">
                <label htmlFor="videoPath" style={{fontSize: '1.5rem', marginRight: '8px'}}>Upload video:</label>
                <input type="file" onChange={event => setVideoFile(event.target.files[0])} name="videoPath" id="videoPath"/>
            </div>
            <div className="w-100 mb-3 d-flex align-items-center" style={{height: '5rem'}}>
                <Button onClick={uploadHandler} className="me-3" >Upload</Button>
                {loading ?
                    <div className="d-flex align-items-center w-100">
                        <div style={{color: '#1266F1', fontSize: '1.2rem'}}>Uploading video. Please wait.</div>
                        <PulseLoader color="#1266F1" className="w-50" />
                    </div>
                    : null}
            </div>
            <div >


            </div>

            
        </div>
    );
};

export default AddVideo;