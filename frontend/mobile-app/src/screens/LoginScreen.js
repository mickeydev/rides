import React, { useState } from 'react';
import { View, Text, TextInput, TouchableOpacity, Alert } from 'react-native';
import { styled } from 'nativewind';
import apiClient, { storeToken } from '../api/client';

const StyledView = styled(View);
const StyledText = styled(Text);
const StyledTextInput = styled(TextInput);
const StyledTouchableOpacity = styled(TouchableOpacity);

const LoginScreen = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const handleLogin = async () => {
    try {
      const response = await apiClient.post('/auth/login', { email, password });
      if (response.data && response.data.accessToken) {
        await storeToken(response.data.accessToken);
        Alert.alert('Success', 'You are logged in!');
        // Here you would navigate to the main part of the app
      }
    } catch (error) {
      console.error('Login error', error);
      Alert.alert('Login Failed', 'Please check your email and password.');
    }
  };

  return (
    <StyledView className="flex-1 justify-center items-center bg-gray-100">
      <StyledView className="w-4/5">
        <StyledText className="text-4xl font-bold text-center mb-10">
          Welcome Back
        </StyledText>

        <StyledTextInput
          className="bg-white p-4 rounded-lg mb-4 text-lg"
          placeholder="Email"
          value={email}
          onChangeText={setEmail}
          keyboardType="email-address"
          autoCapitalize="none"
        />

        <StyledTextInput
          className="bg-white p-4 rounded-lg mb-6 text-lg"
          placeholder="Password"
          value={password}
          onChangeText={setPassword}
          secureTextEntry
        />

        <StyledTouchableOpacity
          className="bg-black py-4 rounded-lg"
          onPress={handleLogin}
        >
          <StyledText className="text-white text-center font-bold text-lg">
            Login
          </StyledText>
        </StyledTouchableOpacity>

        <StyledView className="flex-row justify-center mt-6">
          <StyledText className="text-gray-500">Don't have an account? </StyledText>
          <StyledTouchableOpacity>
            <StyledText className="font-bold">Sign Up</StyledText>
          </StyledTouchableOpacity>
        </StyledView>
      </StyledView>
    </StyledView>
  );
};

export default LoginScreen;
