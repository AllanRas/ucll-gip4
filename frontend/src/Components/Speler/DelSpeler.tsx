import {useNavigate, useParams} from "react-router-dom";
import React, {useEffect} from "react";
import axios from "axios";
import moment from 'moment';
import {Button, Container} from "react-bootstrap";

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


const DelSpeler = () => {
    let params = useParams();
    let navigate = useNavigate();

    const getSpelerURL = "http://localhost:8080/api/spelers/" + params.id + "/getOne";

    const delSpelerURL = "http://localhost:8080/api/spelers/" + params.id + "/delete";

    const [speler, setSpeler] = React.useState<Speler | any>(    {
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

    const Delete = async () => {
        await axios.put<Speler>(delSpelerURL, {},{
            withCredentials: true
        }).then((response) => {
            getSpelers();
        })
    }

    return (
        <>
            <Container className="col-5 bg-dark text-white-50">
                <div>
                    <br/>
                    <h1>Speler: {speler.userDTO.username} </h1>
                    <br/>
                    <h3>Voornaam: {speler.userDTO.voornaam} </h3>
                    <h3>Achternaam: {speler.userDTO.achternaam}</h3>
                    <h3>Email: {speler.userDTO.email}</h3>
                    <h3>Geboortedatum: {moment(speler.geboortedatum).format('DD/MM/YYYY')}</h3>
                    <h3>Actief: {speler.actief ? "true": "false"} </h3>
                    <br/>
                    <h3>Adres </h3>
                    <h4>Gemeente: {speler.adresDTO.gemeente}</h4>
                    <h4>Postcode: {speler.adresDTO.postcode}</h4>
                    <h4>Straat: {speler.adresDTO.straat}</h4>
                    <h4>Huisnr.: {speler.adresDTO.huisnummer}</h4>
                    <br/>
                    {speler.actief?
                        <Button variant={"danger"}  onClick={() => Delete()}>
                            Deactiveren
                        </Button>
                        :
                        <Button variant={"primary"} onClick={() => Delete()}>
                            Activeren
                        </Button>
                    }
                        <Button onClick={() => navigate(-1)}>
                            Terug
                        </Button>
                </div>
            </Container>
        </>
    )
}

export default DelSpeler