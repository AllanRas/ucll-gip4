import React, {ChangeEvent, useEffect, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import axios from "axios";
import {Button, Col, Container, Form, Row} from "react-bootstrap";
import moment from "moment";

interface Speler {
    id: number;
    actief: boolean;
    adresDTO: {
        id: number;
        gemeente: string;
        straat: string;
        huisnummer: string;
        postcode:string;
    }
    geboortedatum: string;
    userDTO: {
        id: number;
        achternaam: string;
        email: string;
        role: string;
        username: string;
        voornaam: string;
    }
}

const EditSpeler = () => {
    let params = useParams();
    let navigate = useNavigate();

    const getSpelerURL = "http://localhost:8080/api/spelers/" + params.id + "/getOne";

    const postSpelerURL = "http://localhost:8080/api/spelers/" + params.id + "/update";


    const [speler, setSpeler] = useState<Speler>(    {
        id: 0,
        actief: false,
        adresDTO: {
            id: 0,
            gemeente:'',
            straat:'',
            huisnummer: '',
            postcode:'',
        },
        geboortedatum: '',
        userDTO: {
            id: 0,
            achternaam: '',
            email: '',
            role: '',
            username: '',
            voornaam: '',
        }
    });

    // get spelers from api
    useEffect(() => {
        getSpelers();
    }, []);

    const getSpelers = async () => {
        await axios.get<Speler>(getSpelerURL, {
            withCredentials: true
        }).then((response) =>{
            setSpeler(response.data);
        })
    }

    // POST add speler
    const PutSpeler = async () => {
        await axios.put<Speler>(postSpelerURL, speler, {
            headers: {
                'Content-Type': 'application/json'
            },
            withCredentials: true
        }).then((response) => {
            navigate("/Spelers/" + speler.id);
        }).catch(err => {
            console.log(err);
        });
    };

    // de onChange functie voor speler
    const ChangeHandlerSpeler = (event: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        setSpeler(prevSpeler => ({
                ...prevSpeler,
                [event.target.name]: event.target.value
            })
        )
    }

    // de onChange functie voor UserDTO
    const ChangeHandlerUserDTO = (event: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        setSpeler(prevSpeler => ({
                ...prevSpeler,
                userDTO:{...prevSpeler.userDTO ,[event.target.name] : event.target.value}
            })
        )
    }

    // de onChange functie voor AdresDTO
    const ChangeHandlerAdresDTO = (event: ChangeEvent<HTMLInputElement|HTMLTextAreaElement>) => {
        setSpeler(prevCreateSpeler => ({
                ...prevCreateSpeler,
                adresDTO: {...prevCreateSpeler.adresDTO, [event.target.name]: event.target.value}
            })
        )
    }

    return (
        <>
            <Container className="col-md-5 bg-dark text-white-50">
                <h1>Speler aanpassen</h1>
                <br/>
                <Form>
                    <Form.Group className={"mb-3"}>
                        <Form.Label>Username</Form.Label>
                        <Form.Control name="username"
                                      type={"text"}
                                      placeholder="Username"
                                      value={speler?.userDTO.username}
                                      onChange={ChangeHandlerUserDTO}
                        />
                    </Form.Group>

                    <Row>
                        <Col>
                            <Form.Group className={"mb-3"}>
                                <Form.Label>Voornaam</Form.Label>
                                <Form.Control name="voornaam"
                                              type={"text"}
                                              placeholder="Voornaam"
                                              value={speler?.userDTO.voornaam}
                                              onChange={ChangeHandlerUserDTO}
                                />

                            </Form.Group>
                        </Col>
                        <Col>
                            <Form.Group className={"mb-3"}>
                                <Form.Label>Achternaam</Form.Label>
                                <Form.Control name="achternaam"
                                              type={"text"}
                                              placeholder="Achternaam"
                                              value={speler?.userDTO.achternaam}
                                              onChange={ChangeHandlerUserDTO}
                                />
                            </Form.Group>
                        </Col>
                    </Row>

                    <Form.Group className={"mb-3"}>
                        <Form.Label>Email</Form.Label>
                        <Form.Control name="email"
                                      type={"email"}
                                      placeholder="Email"
                                      value={speler?.userDTO.email}
                                      onChange={ChangeHandlerUserDTO}
                        />
                    </Form.Group>

                    <Row>
                        <Col>
                            <Form.Group className={"mb-3"}>
                                <Form.Label>Gemeente</Form.Label>
                                <Form.Control name="gemeente"
                                              type={"text"}
                                              placeholder="Gemeente"
                                              value={speler?.adresDTO.gemeente}
                                              onChange={ChangeHandlerAdresDTO}
                                />
                            </Form.Group>
                        </Col>
                        <Col xs lg="3">
                            <Form.Group className={"mb-3"}>
                                <Form.Label>Postcode</Form.Label>
                                <Form.Control name="postcode"
                                              type={"text"}
                                              placeholder="Postcode"
                                              value={speler?.adresDTO.postcode}
                                              onChange={ChangeHandlerAdresDTO}
                                />
                            </Form.Group>
                        </Col>
                    </Row>

                    <Row>
                        <Col>
                            <Form.Group className={"mb-3"}>
                                <Form.Label>Straat</Form.Label>
                                <Form.Control name="straat"
                                              type={"text"}
                                              placeholder="Straat"
                                              value={speler?.adresDTO.straat}
                                              onChange={ChangeHandlerAdresDTO}
                                />
                            </Form.Group>
                        </Col>
                        <Col xs lg="2">
                            <Form.Group className={"mb-3"}>
                                <Form.Label >huisnr.</Form.Label>
                                <Form.Control name="huisnummer"
                                              type={"text"}
                                              value={speler?.adresDTO.huisnummer}
                                              onChange={ChangeHandlerAdresDTO}
                                />
                            </Form.Group>
                        </Col>
                    </Row>

                    <Form.Group className={"mb-3"}>
                        <Form.Label>Geboortedatum</Form.Label>
                        <Form.Control name="geboortedatum"
                                      type={"date"}
                                      placeholder="geboortedatum"
                                      value={moment(speler.geboortedatum).format("yyyy-MM-DD")}
                                      onChange={ChangeHandlerSpeler}
                        />
                    </Form.Group>

                    <Row className="justify-content-center">
                        <Col lg="3">
                            <Button variant={"primary"}  onClick={() => PutSpeler()}>
                                Submit
                            </Button>
                        </Col>
                        <Col>
                            <Button onClick={() => navigate("/Spelers/" + speler.id)}>
                                Cancel
                            </Button>
                        </Col>
                    </Row>
                </Form>
            </Container>
        </>
    )
}

export default EditSpeler