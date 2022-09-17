import React from 'react';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faUsers} from "@fortawesome/free-solid-svg-icons";

const SubscriptionAbout = () => {
    return (
        <div className="mt-2">
            <h1>Subscriptions <FontAwesomeIcon  icon={faUsers} /></h1>
        </div>
    );
};

export default SubscriptionAbout;