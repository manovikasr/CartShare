import React, { Component } from "react";
import { withRouter } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import axios from "axios";
import { Alert } from "react-bootstrap";

class PoolApplications extends Component {
    constructor(props) {
        super(props);
        this.state = {
            user_applications: []
        };
    }

    componentDidMount() {
        this.getApplications();
    }

    getApplications = () => {
        const { user } = this.props.auth;
        const pool = this.props.pool;
        axios.get(`application/reference/${pool.id}/${user.screen_name}`)
            .then(res => {
                if (res.data) {
                    this.setState({
                        user_applications: res.data.pool_Applications_List
                    });
                }
            })
            .catch(e => {
                if (e.response)
                    console.log(e.response.data);
            });
    };

    render() {
        const { user } = this.props.auth;
        var applications;

        if (this.state.user_applications.length) {
            applications = this.state.user_applications.map(application => <b>{application.requserscreenname}</b>);
        } else {
            applications = (
                <Alert variant="warning">
                    There are no pending applications in this pool.
                </Alert>
            );
        }
        return (
            <div>
                {applications}
            </div>
        );
    }
}

PoolApplications.propTypes = {
    auth: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
    auth: state.auth
});


export default connect(mapStateToProps, {})(withRouter(PoolApplications));