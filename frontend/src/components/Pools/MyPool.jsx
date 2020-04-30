import React, { Component } from "react";
import { withRouter } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { Alert, Card, Button, Row, Col } from "react-bootstrap";
import AddEditPoolModal from "./AddEditPoolModal";
import poolImage from "../../images/PoolImage.jpeg";
import PoolApplications from "./PoolApplications";

class MyPool extends Component {
    constructor(props) {
        super(props);
        this.state = {
            showModal: false,
            pool: null
        };
    }

    componentDidMount() {
        if (!this.props.auth.isAuthenticated && this.props.auth.user.role === "admin") {
            this.props.history.push("/");
        }
        else {
            if (!this.props.auth.user.email_verified) {
                this.props.history.push("/verify");
            }
        }
        this.getPoolData();
    }

    handleToggle = () => {
        this.setState({
            showModal: !this.state.showModal
        });
    };

    getPoolData = () => {

    };

    render() {
        const { user } = this.props.auth;
        var poolInfo, actionButton;
        if (!this.state.pool) {
            poolInfo = (
                <div>
                    <Alert variant="warning">You are not part of any pool yet.</Alert>
                    <br />
                    <center>
                        <Button variant="success" onClick={this.handleToggle}>Create Pool</Button>
                        <br /><br />OR<br /><br />
                        <Button variant="primary" href="/pool/all">Join Pool</Button>
                    </center>
                </div>
            )
        } else {
            if (this.state.pool && this.state.pool.leader) {
                actionButton = (
                    <div>
                        <Button variant="primary" onClick={this.handleToggle}>Update Pool</Button>&nbsp;&nbsp;&nbsp;
                        <Button variant="danger" onClick={this.deletePool}>Delete Pool</Button>
                    </div>
                );
            } else {
                actionButton = (
                    <Button variant="warning" onClick={this.leavePool}>Leave Pool</Button>
                );
            }
            poolInfo = (
                <div>
                    <Row>
                        <Card style={{ width: "70rem", height: "16rem", margin: "2%" }}>
                            <Row>
                                <Col>
                                    <Card.Img style={{ width: "18rem", height: "15rem" }} src={poolImage} />
                                </Col>
                                <Card.Body>
                                    <Card.Title><h1>{}</h1></Card.Title>
                                    <br />
                                    <Card.Text>
                                        <h4>

                                        </h4>
                                    </Card.Text>
                                </Card.Body>
                            </Row>
                        </Card>
                    </Row>
                    <br />
                    <center>
                        {actionButton}
                    </center>
                    <br />
                    <PoolApplications />
                </div>
            );
        }

        return (
            <div style={{ height: "75vh" }} className="container valign-wrapper">
                <br />
                {poolInfo}
                <AddEditPoolModal pool={this.state.pool} showModal={this.state.showModal} onHide={this.handleToggle} getPoolData={this.getPoolData} />
            </div>
        );
    }
}

MyPool.propTypes = {
    auth: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
    auth: state.auth
});


export default connect(mapStateToProps, {})(withRouter(MyPool));