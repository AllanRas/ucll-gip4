import {useNavigate, useParams} from "react-router-dom";
import React, {useEffect} from "react";
import axios from "axios";
import {Button, Container, Table} from "react-bootstrap";

interface Team {
    id : number
    naam : string
    actief : boolean
    managerDTO : {
        id: number
        userDTO: {
            achternaam: string
            email: string
            id: number
            role: string
            username: string
            voornaam: string
        }
    }
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


const Team = () => {
    let params = useParams();
    let navigate = useNavigate();

    const getTeamURL = "http://localhost:8080/api/teams/" + params.id + "/getOne";
    const getSpelersURL = "http://localhost:8080/api/spelers";
    const [spelers, setSpelers] = React.useState<Speler[]>([]);

    const [team, setTeam] = React.useState<Team>({
        actief: false,
        id: 0,
        managerDTO: {id: 0, userDTO: {achternaam: "", email: "", id: 0, role: "", username: "", voornaam: ""}},
        naam: "",
        spelerDTO: []
    });

    useEffect(() => {
        axios.get<Team>(getTeamURL, {
            withCredentials : true
        }).then((response) =>{
            console.log(response.data);
            setTeam(response.data);
        });

        getSpelers();
    }, [getTeamURL]);


    const getSpelers = () => {
        axios.get<Speler[]>(getSpelersURL, {
            withCredentials: true
        }).then((response) =>{
            setSpelers(response.data);
        }).catch((e) => {
            console.log(e);
        });
    }

    return (
        <>
            <Container className="col-8 bg-dark text-white-50" >
                <div>
                    <h1>{team.naam}</h1>

                    <h2>Manager: {team.managerDTO.userDTO.username}</h2>
                    <br/>
                    <Button onClick={() => navigate("/Teams/" + team.id + "/AddSpeler")}>
                        Spelers
                    </Button>
                    <Button onClick={() => navigate("/Teams/ "+ team.id + "/EditTeamnaam")}>
                        Team naam aanpassen
                    </Button>
                    <Button onClick={() => navigate("/Teams")}>
                        Terug
                    </Button>
                </div>

                <br/>

                <h2>Spelers :</h2>

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
                                team.spelerDTO.some(spelerDTO => spelerDTO.spelerid === speler.id && !spelerDTO.reserve) ?
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

                <h2>Reserve spelers :</h2>

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
                                team.spelerDTO.some(spelerDTO => spelerDTO.spelerid === speler.id && spelerDTO.reserve) ?
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
            </Container>
        </>
    )
}

export default Team