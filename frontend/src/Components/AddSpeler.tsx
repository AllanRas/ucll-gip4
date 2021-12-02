import {Button, Form} from "react-bootstrap";
import React from "react";
import axios from "axios";

interface Speler {
    adresDTO: {
        gemeente: string;
        straat: string;
        huisnummer: string;
        postcode:string;
    }
    geboortedatum: string;
    userDTO: {
        achternaam: string;
        email: string;
        username: string;
        voornaam: string;
    }
}

const postSpelerURL = "http://localhost:8080/api/spelers";

const AddSpeler = () => {

    const PostSpeler = () => {
        axios.post<Speler>(postSpelerURL, {}, {
            auth: {
                username: 'manager',
                password: 'manager'
            }
        }).then((response) => {
            console.log(response.data);
        })};

        const [speler, setSpeler] = React.useState<Speler>();

        return (
            <>
                <Form>
                    <Form.Group className={"mb-3"}>
                        <Form.Label>Username</Form.Label>
                        <Form.Control type={"text"} placeholder="Username"></Form.Control>
                    </Form.Group>

                    <Form.Group className={"mb-3"}>
                        <Form.Label>Password</Form.Label>
                        <Form.Control type={"password"} placeholder="Password"></Form.Control>
                    </Form.Group>

                    <Form.Group className={"mb-3"}>
                        <Form.Label>Voornaam</Form.Label>
                        <Form.Control type={"test"} placeholder="Voornaam"></Form.Control>
                    </Form.Group>

                    <Form.Group className={"mb-3"}>
                        <Form.Label>Achternaam</Form.Label>
                        <Form.Control type={"test"} placeholder="Achternaam"></Form.Control>
                    </Form.Group>

                    <Form.Group className={"mb-3"}>
                        <Form.Label>Email</Form.Label>
                        <Form.Control type={"email"} placeholder="Email"></Form.Control>
                    </Form.Group>

                    <Form.Group className={"mb-3"}>
                        <Form.Label>Gemeente</Form.Label>
                        <Form.Control type={"text"} placeholder="Gemeente"></Form.Control>
                    </Form.Group>

                    <Form.Group className={"mb-3"}>
                        <Form.Label>Straatnaam</Form.Label>
                        <Form.Control type={"text"} placeholder="Straatnaam"></Form.Control>
                    </Form.Group>

                    <Form.Group className={"mb-3"}>
                        <Form.Label>Huisnummer</Form.Label>
                        <Form.Control type={"text"} placeholder="Huisnummer"></Form.Control>
                    </Form.Group>

                    <Form.Group className={"mb-3"}>
                        <Form.Label>Postcode</Form.Label>
                        <Form.Control type={"text"} placeholder="Postcode"></Form.Control>
                    </Form.Group>

                    <Form.Group className={"mb-3"}>
                        <Form.Label>Geboortedatum</Form.Label>
                        <Form.Control type={"date"} placeholder="geboortedatum"></Form.Control>
                    </Form.Group>

                    <Button variant={"primary"} onClick={() => PostSpeler()}>
                        Submit
                    </Button>
                </Form>
            </>
        )
    }

export default AddSpeler