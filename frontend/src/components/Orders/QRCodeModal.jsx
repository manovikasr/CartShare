import React, { Component } from "react";
import { withRouter } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { Button, Modal, Form, Col, Alert } from "react-bootstrap";
import axios from "axios";
import { QRCode } from "react-qr-svg";

class QRCodeModal extends Component {
    constructor(props) {
        super(props);
        this.state = {
            qr_code_value:""
        };
    }

    componentDidMount = () => {
    }

    onHide = (e) => {
        this.props.onHide();
    }

    render() {
        var qrcode, text = Math.random().toString(36).substring(2,35);
        qrcode = (
            <div>
                <QRCode
                    bgColor="#FFFFFF"
                    fgColor="#000000"
                    level="Q"
                    style={{ width: 200 }}
                    value={text}
                />
            </div>
        )
        return (
            <Modal show={this.props.showModal} onHide={this.onHide}>
                <Modal.Header closeButton>
                    <Modal.Title><b>Scan this QR code to checkout</b></Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form>
                        <Form.Row>
                                <Form.Group controlId="produt_id" style = {{ marginLeft: "30%" }}>
                                    <Form.Label>{qrcode}</Form.Label>
                                </Form.Group>
                        </Form.Row>

                        <center>
                            <Button variant="success" onClick={this.props.onHide}>
                                <b>Checkout</b>
                            </Button>&nbsp;&nbsp;

                            <Button variant="secondary" onClick={this.props.onHide}>
                                <b>Close</b>
                            </Button>
                        </center>
                    </Form>
                </Modal.Body>
            </Modal>
        );
    }
}

QRCodeModal.propTypes = {
    auth: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
    auth: state.auth
});

export default connect(mapStateToProps, {})(withRouter(QRCodeModal));