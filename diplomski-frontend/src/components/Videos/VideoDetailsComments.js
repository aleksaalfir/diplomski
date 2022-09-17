import React from 'react';
import {Button} from "react-bootstrap";
import moment from "moment";
import Comment from "./Comment/Comment";

const VideoDetailsComments = props => {

    let commentsRender = props.videoComments
        .sort((a,b)=> new Date(b.date).getTime() - new Date(a.date).getTime())
        .map((comment, index) =>{
            return <Comment key={index} date={comment.date} channelName={comment.user.channelName} comment={comment.comment}/>
        })

    return (
        <div>
            <h4>Comments</h4>
            <div className="d-flex">
                <textarea className="w-75" placeholder="Write your comment..." name="comment" onChange={(event)=> props.changeCommentHandler(event.target.value)}/>
                <Button onClick={props.submitCommentHandler} className="ms-2 btn-danger">Submit</Button>
            </div>
            <div className="d-flex flex-column me-2">
                {commentsRender.length === 0 ? <strong className="mt-2">For this video the comments have not been found.</strong> : commentsRender}
            </div>
        </div>
    );
};

export default VideoDetailsComments;