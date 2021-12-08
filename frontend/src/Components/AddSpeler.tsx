import {Button, Container, Form, Col, Row} from "react-bootstrap";
import React, {ChangeEvent, useState} from "react";
import axios from "axios";
import {useNavigate} from "react-router-dom";

interface CreateSpeler {
    adresDTO: {
        gemeente: string;
        straat: string;
        huisnummer: string;
        postcode:string;
    }
    geboortedatum: string;
    password: string;
    userDTO: {
        username: string;
        achternaam: string;
        email: string;
        voornaam: string;
    }
}

const postSpelerURL = "http://localhost:8080/api/spelers";

const AddSpeler = () => {

        let navigate = useNavigate();

        //
        const [speler, setSpeler] = useState<CreateSpeler>({
            geboortedatum: "",
            adresDTO: {
                gemeente: "",
                huisnummer: "",
                postcode: "",
                straat: ""
            },
            password: "",
            userDTO: {
                achternaam: "",
                email: "",
                username: "",
                voornaam: "",
            }
        });

        // POST add speler
        const PostSpeler = async () => {
            console.log(speler)
            await axios.post<CreateSpeler>(postSpelerURL, speler, {
                headers: {
                    'Content-Type': 'application/json'
                },withCredentials : true
            }).then((response) => {
                console.log(response.data);
                navigate("/Spelers");
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
            console.log(speler);
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
                    <h1>Speler Toevoegen</h1>

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

                        <Form.Group className={"mb-3"}>
                            <Form.Label>Password</Form.Label>
                            <Form.Control name="password"
                                          type={"password"}
                                          placeholder="Password"
                                          value={speler?.password}
                                          onChange={ChangeHandlerSpeler}
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
                                          value={speler?.geboortedatum}
                                          onChange={ChangeHandlerSpeler}
                            />
                        </Form.Group>

                        <Row className="justify-content-center">
                            <Col lg="3">
                                <Button variant={"primary"}  onClick={() => PostSpeler()}>
                                    Submit
                                </Button>
                            </Col>
                            <Col>
                                <Button onClick={() => navigate("/Spelers")}>
                                    Cancel
                                </Button>
                            </Col>
                        </Row>
                    </Form>
                </Container>
            </>
        )
    }

export default AddSpeler