import React, { Component } from "react";
import { withRouter } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import axios from "axios";
import { Alert, Card, Row, Col, Button } from "react-bootstrap";

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

    componentWillReceiveProps() {
        this.getApplications();
    }

    getApplications = () => {
        const { user } = this.props.auth;
        const pool = this.props.pool;
        if (pool.pool_leader_id === user.id) {
            axios.get(`application/pending/${pool.id}`)
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
        } else {
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
        }
    };

    onClick = (e) => {

        const pool_id = this.props.pool.id;
        const user_id = parseInt(e.target.name);
        const approval = e.target.value === "true";
        const application_id = e.target.id;

        axios.post(`application/approve?pool_id=${pool_id}&user_id=${user_id}&approval=${approval}&application_id=${application_id}`)
            .then(res => {
                if (res.status === 200) {
                    this.props.getPoolData();
                }
            })
            .catch(e => {
                if (e.response)
                    console.log(e.response.data);
            });
    };

    onSupportClick = (e) => {

        const support = e.target.value === "true";
        const application_id = e.target.id;

        axios.post(`application/support?application_id=${application_id}&support=${support}`)
            .then(res => {
                if (res.status === 200) {
                    this.props.getPoolData();
                }
            })
            .catch(e => {
                if (e.response)
                    console.log(e.response.data);
            });
    };

    render() {
        const { user } = this.props.auth;
        const pool = this.props.pool;
        var isPoolLeader, applicationCard;
        if (user.id === pool.pool_leader_id)
            isPoolLeader = true;
        var applications = [], buttons;
        if (pool.user.length < 4) {
            if (this.state.user_applications.length) {
                this.state.user_applications.forEach(application => {

                    if (isPoolLeader) {
                        applicationCard = (
                            <Card bg="white" style={{ width: "40rem", margin: "2%" }}>
                                <Row>
                                    <Card.Body>
                                        <Card.Title>{application.requserscreenname}</Card.Title>
                                        <Card.Text>
                                            <b>Reference Name: </b>{application.refusername}<br />
                                            <b>Status: </b>Supported<br />
                                        </Card.Text>
                                    </Card.Body>
                                    <Col>
                                        <br />
                                        <Button variant="success" id={application.id} name={application.requserid} value={true} onClick={this.onClick}>Approve</Button>&nbsp;&nbsp;&nbsp;
                                    <Button variant="warning" id={application.id} name={application.requserid} value={false} onClick={this.onClick}>Reject</Button>
                                    </Col>
                                </Row>
                            </Card >
                        )
                    } else {
                        if (!application.refsupportstatus) {
                            buttons = (
                                <>
                                    <Button variant="primary" id={application.id} name={application.requserid} value={true} onClick={this.onSupportClick}>Support</Button>&nbsp;&nbsp;&nbsp;
                                    <Button variant="secondary" id={application.id} name={application.requserid} value={false} onClick={this.onSupportClick}>Remove</Button>
                                </>
                            );
                        } else {
                            buttons = (
                                <Button variant="primary" >Supported</Button>
                            )
                        }


                        applicationCard = (
                            <Card bg="white" style={{ width: "40rem", margin: "2%" }}>
                                <Row>
                                    <Card.Body>
                                        <Card.Title>{application.requserscreenname}</Card.Title>
                                        <Card.Text>
                                            <b>Reference Name: </b>{application.refusername}<br />
                                        </Card.Text>
                                    </Card.Body>
                                    <Col>
                                        <br />
                                        {buttons}
                                    </Col>
                                </Row>
                            </Card >
                        )
                    }
                    applications.push(applicationCard);
                });
            } else {
                applications = (
                    <Alert variant="warning">
                        There are no pending applications for you to approve.
                    </Alert>
                );
            }
        } else {
            applications = (
                <Alert variant="info">
                    This pool is now full.
                </Alert>
            );
        }
        return (
            <div>
                <h3>Pool Applications</h3>
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