import {Link, useNavigate, useParams} from "react-router-dom";
import React, {ChangeEvent, useEffect, useState} from "react";
import axios from "axios";
import {Button, Col, Container, Form, Row, Table} from "react-bootstrap";
import {map} from "react-bootstrap/ElementChildren";

interface Team {
    id : number
    naam : string
    actief : boolean
    spelerDTO : SpelerDTO[]
}

interface SpelerDTO{
    id : number
    reserve : boolean
    spelerid: number
}

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

const AddSpelerToTeam = () => {

    let params = useParams();
    let navigate = useNavigate();

    // get Team
    const getTeamURL = "http://localhost:8080/api/teams/" + params.id + "/getOne";
    const [team, setTeam] = useState<Team>({
        spelerDTO: [],
        actief: false,
        naam: "",
        id: 0
        }
    );

    //get spelers
    const getSpelersURL = "http://localhost:8080/api/spelers";
    const [spelers, setSpelers] = React.useState<Speler[]>([]);


    useEffect(() => {
        axios.get<Team>(getTeamURL, {
            headers: {
                'Content-Type': 'application/json'
            },withCredentials : true
        }).then((response) => {
            setTeam(response.data);
        }).catch(err => {
            console.log(err);

        });

        console.log(team);

        axios.get<Speler[]>(getSpelersURL, {
            withCredentials: true
        }).then((response) =>{
            setSpelers(response.data);
        }).catch((e) => {
            console.log(e);
        });
    },[]);

    //add speler to team
    const teamsURL = "http://localhost:8080/api/teams";
    const PostAddSpeler = async (spelerId : number , reserve: boolean) => {
        await axios.post<Team>(teamsURL + "/" + params.id + "/AddSpeler/"+  + spelerId + "/" + reserve,{}, {
            headers: {
                'Content-Type': 'application/json'
            },withCredentials : true
        }).then((response) => {

        }).catch(err => {
            console.log(err);
        });
    };


    return (
        <>
            <Container className="col-md-11 bg-dark text-white-50">

                <h1>team : {team.naam}</h1>
                <br/>
                <h2>Spelers : </h2>

                <Table striped bordered hover variant={'dark'}>
                    <thead>
                    <tr>
                        <th>id</th>
                        <th>username</th>
                        <th>voornaam</th>
                        <th>achternaam</th>
                    </tr>
                    </thead>

                    <tbody>
                    {
                        spelers.map(speler => (
                                team.spelerDTO.some(spelerDTO => spelerDTO.spelerid === speler.id) ?
                                    <tr key={speler.id}>
                                        <td>{speler.id}</td>
                                        <td>{speler.userDTO.username}</td>
                                        <td>{speler.userDTO.voornaam}</td>
                                        <td>{speler.userDTO.achternaam}</td>
                                    </tr>
                            : ""
                            )
                        )
                    }

                    </tbody>
                </Table>

                <h2>Spelers Toevoegen</h2>

                <Table striped bordered hover variant={'dark'}>
                    <thead>
                    <tr>
                        <th>id</th>
                        <th>username</th>
                        <th>voornaam</th>
                        <th>achternaam</th>
                        <th> </th>
                    </tr>
                    </thead>

                    <tbody>
                    {
                        spelers.map(speler => (
                            team.spelerDTO.some(spelerDTO => spelerDTO.spelerid === speler.id) ? "" :
                                <tr key={speler.id}>
                                    <td>{speler.id}</td>
                                    <td>{speler.userDTO.username}</td>
                                    <td>{speler.userDTO.voornaam}</td>
                                    <td>{speler.userDTO.achternaam}</td>
                                    <td><Button onClick={() => PostAddSpeler(speler.id, false)}> Speler toevoegen </Button></td>
                                </tr>
                            )
                        )
                    }

                    </tbody>
                </Table>


            </Container>
        </>
    )
}

export default AddSpelerToTeam