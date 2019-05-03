/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from "react";
import {
  Modal,
  Text,
  TouchableHighlight,
  View,
  Platform,
  StatusBar
} from "react-native";

export default class App extends Component {
  state = {
    modalVisible: false
  };

  setModalVisible(visible) {
    this.setState({ modalVisible: visible });
  }

  render() {
    return (
      <View
        style={{
          position: "absolute",
          top: 0,
          bottom: 0,
          left: 0,
          right: 0,
          padding: 32
        }}
      >
        {Platform.OS === "android" && (
          <StatusBar
            barStyle={"dark-content"}
            backgroundColor={"#ff0000"}
            animated={true}
            translucent={false}
          />
        )}
        <Modal
          animationType="fade"
          transparent={true}
          visible={this.state.modalVisible}
          onRequestClose={() => {
            this.setModalVisible(!this.state.modalVisible);
          }}
        >
          <View
            style={{
              flex: 1,
              justifyContent: "space-between",
              alignItems: "center",
              padding: 0,
              backgroundColor: "rgba(0,0,0,0.5)"
            }}
          >
            <Text>Hello World!</Text>
            <TouchableHighlight
              onPress={() => {
                this.setModalVisible(!this.state.modalVisible);
              }}
            >
              <Text>Hide Modal</Text>
            </TouchableHighlight>
          </View>
        </Modal>

        <TouchableHighlight
          onPress={() => {
            this.setModalVisible(true);
          }}
        >
          <Text>Show Modal</Text>
        </TouchableHighlight>
      </View>
    );
  }
}
