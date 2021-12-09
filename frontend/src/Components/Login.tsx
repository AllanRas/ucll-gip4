import {Button, Col, Container, Form, FormControl, FormGroup, FormLabel} from "react-bootstrap";
import React, {ChangeEvent, useState} from "react";
import axios from "axios";
import {useNavigate} from "react-router-dom";
import Header from "./Header";

interface IUser{
    username: string,
    password: string
}

const Login = () => {

    let navigate = useNavigate();
    const loginURL = 'http://localhost:8080/api/login/user'

    const [user, setUser] = useState<IUser>(
        {
            username: '',
            password: ''
        }
    );

    // POST login speler
    const PostLogin = async () => {
        console.log(user)
        await axios.post<IUser>(loginURL, user,{
            auth:{
                username: user.username,
                password: user.password
            },
            withCredentials: true,
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Headers' : '*',
                'Access-Control-Allow-Credentials': 'include'},

        }).then((response) => {

            console.log("Logged in")
            console.log(response.data);
            navigate("/Spelers")
        }).catch(err => {
            console.log(err);
        });
    };

    const ChangeHandlerLogin = (event: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        setUser(prevUser => ({
                ...prevUser,
                [event.target.name]: event.target.value
            })
        )
    }


    return (
        <>
            <Container className="col-md-5">
                <Form>
                    <FormGroup>
                        <FormLabel>Username</FormLabel>
                        <FormControl name="username"
                                     type="text"
                                     value={user.username}
                                     onChange={ChangeHandlerLogin}
                        />
                    </FormGroup>
                    <FormGroup>
                        <FormLabel>Username</FormLabel>
                        <FormControl name="password"
                                     type="password"
                                     value={user.password}
                                     onChange={ChangeHandlerLogin}
                        />
                    </FormGroup>

                    <Col lg="3">
                        <Button variant={"primary"}  onClick={() => PostLogin()}>
                            Submit
                        </Button>
                    </Col>
                </Form>
            </Container>
        </>
    )
}

export default Login