import React, { Component } from "react";
import { withRouter } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { Button, Modal } from "react-bootstrap";
import axios from "axios";
import { QRCode } from "react-qr-svg";

class QRCodeModal extends Component {
    constructor(props) {
        super(props);
        this.state = {
            order_ids: []
        };
    }

    confirmPickup = (e) => {
        const order_ids = this.props.orders.map(order => order.id);
        var order_id_string = "";
        order_ids.forEach(id => {
            order_id_string += id + ",";
        });
        order_id_string = order_id_string.substring(0, order_id_string.length - 1);

        axios.put(`/order/pickup?order_ids=${order_id_string}`)
            .then(res => {
                if (res.status === 200) {
                    this.props.onHide();
                    this.props.history.push("/orders/deliver");
                }
            })
            .catch(e => {
                console.log(e);
            });
    }

    render() {
        var qrcode;
        var order_ids = this.props.orders.map(order => order.id);
        qrcode = (
            <div>
                <QRCode
                    bgColor="#FFFFFF"
                    fgColor="#000000"
                    level="Q"
                    style={{ width: 200 }}
                    value={order_ids.toString()}
                />
            </div>
        );
        return (
            <Modal show={this.props.showModal} onHide={this.props.onHide}>
                <Modal.Header closeButton>
                    <Modal.Title><b>Scan this QR code to checkout</b></Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <center>
                        {qrcode}
                        <br /><br />
                        <Button variant="success" onClick={this.confirmPickup}>
                            <b>Checkout</b>
                        </Button>&nbsp;&nbsp;
                            <Button variant="secondary" onClick={this.props.onHide}>
                            <b>Close</b>
                        </Button>
                    </center>
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