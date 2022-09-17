import React from 'react';
import moment from "moment";

const Comment = (props) => {
    return (
        <div className="w-100 mt-2 p-2" style={{border:"1px solid black", borderRadius: '1.5rem'}}>
            <h6>{props.channelName} <span style={{color: "gray"}}>{moment.utc(props.date).local().startOf('seconds').fromNow()}</span></h6>
            <div>{props.comment}</div>
        </div>
    );
};

export default Comment;